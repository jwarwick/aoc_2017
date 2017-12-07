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
            r (root tree)]
        (println "Part 1:" r)
        (println "Part 2: tbd")))))
