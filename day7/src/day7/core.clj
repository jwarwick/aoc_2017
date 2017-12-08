(ns day7.core
  "AOC 2017 Day 7"
  (:require
    [clojure.data :as data]
    [clojure.string :as string])
  (:gen-class))

(defn clean-weight
  [acc input]
  (let [w-str (string/trim (second input))
        w-cln (->> (string/replace w-str #"[\(\)]" "")
                Integer/parseInt)]
    (conj acc (assoc input 1 w-cln))))

(defn clean-children
  [acc input]
  (let [cnt (count input)]
    (cond
      (= 4 cnt)
      (let [children-comma (string/split (last input) #"\s+")
            children (map #(string/replace % #"," "") children-comma)]
        (conj acc (vector (first input) (second input) children)))
      :else
      (conj acc input))))

(defn build-map
  [input]
  (loop [nodes input
         acc {}]
    (if (empty? nodes)
      acc
      (let [n (first nodes)
            k (first n)
            w (second n)
            children (nth n 2 nil)]
      (recur (rest nodes) (assoc acc k {:name k :weight w :children children}))))))

(defn build-tree
  "Parse a tree input string into a data structure"
  [input]
  (let [tree (->> (string/split input #"\n")
                (map string/trim)
                (map #(string/split % #"\s+" 4))
                (reduce clean-weight `())
                (reduce clean-children `())
                build-map)]
    tree))

(defn root
  "Return the key for the root of a tree"
  [tree]
  (let [nodes (set (keys tree))
        children (->> tree
                   (remove #(nil? (:children (val %))))
                   (map #(:children (second %)))
                   flatten
                   set)
        delta (data/diff nodes children)]
    (first (first delta))))

(defn update-node-weights
  [node child-weights]
  (let [total (reduce + (:weight node) child-weights)]
    (assoc node :total-weight total :child-weights child-weights)))

(defn update-node
  [tree n]
  (let [node (tree n)]
    (if (nil? (:children node))
      (assoc tree n (update-node-weights node []))
      (loop [children (:children node)
             curr-tree tree]
        (if (empty? children)
          (let [child-weights (map :total-weight (map curr-tree (:children node)))]
            (assoc curr-tree n (update-node-weights node child-weights)))
          (recur (rest children) (update-node curr-tree (first children))))))))

(defn propagate-weights
  "Travel back out the tree and find where the weights are incorrect"
  [tree n target-weight]
  (let [node (tree n)
        freqs (frequencies (:child-weights node))]
    (if (= 1 (count freqs))
      (- (:weight node) (- (:total-weight node) target-weight))
      (let [sorted (sort-by val freqs)
            new-target (key (last sorted))
            new-node-idx (.indexOf (:child-weights node) (key (first sorted)))
            new-node (nth (:children node) new-node-idx)]
        (propagate-weights tree new-node new-target)))))
      
(defn corrected-weight
  "Assuming one weight is wrong, what should the correct weight be"
  [tree root]
  (let [tree-sums (update-node tree root)]
    (propagate-weights tree-sums root 0)))

(defn -main
  "AOC Day 7 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (let [tree (build-tree input)
            r (root tree)
            cw (corrected-weight tree r)]
        (println "Part 1:" r)
        (println "Part 2:" cw)))))
