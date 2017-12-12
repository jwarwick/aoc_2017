(ns day11.core
  "AOC 2017 Day 11" 
  (:require
    [clojure.string :as string])
  (:gen-class))

;; Using the Axial notation defined here: https://www.redblobgames.com/grids/hexagons/#neighbors

(defn take-step
  "Take one step from the given location"
  [[q r] direction]
  (cond
    (= "n" direction) [q (dec r)]
    (= "ne" direction) [(inc q) (dec r)]
    (= "se" direction) [(inc q) r]
    (= "s" direction) [q (inc r)]
    (= "sw" direction) [(dec q) (inc r)]
    (= "nw" direction) [(dec q) r]
    :else
    (throw (AssertionError. (str "Unknown direction: " direction)))))

(defn walk-path
  "Follow the given path, return the final position"
  [path-str]
  (let [path (string/split path-str #",")]
    (reduce take-step [0 0] path)))

(defn generate-path
  "Follow the given path, return a list of all positions"
  [path-str]
  (let [path-list (string/split path-str #",")]
    (loop [path path-list
           curr-node [0 0]
           all-nodes [[0 0]]]
      (if (empty? path)
        all-nodes
        (let [next-node (take-step curr-node (first path))]
          (recur (rest path) next-node (conj all-nodes next-node)))))))

(defn get-neighbors
  [node]
  (for [dir `("n" "ne" "se" "s" "sw" "nw")]
    (take-step node dir)))

(defn get-unvisited-neighbors
  [node visited]
  (let [neighbors (get-neighbors node)]
    (remove (fn [n] (some #(= n %) visited)) neighbors)))

(defn node-distance
  [[aq ar] [bq br]]
  ( / (+ (Math/abs (- aq  bq))
         (Math/abs (- (- (+ aq ar) bq) br))
         (Math/abs (- ar br)))
      2))

(defn build-entry
  [curr-node target-node new-node]
  {:node new-node
   :priority (+ (inc (count (:path curr-node))) (* 2 (node-distance new-node target-node)))
   :path (vec (concat (:path curr-node) (vector (:node curr-node))))}
  )

(defn search
  "Search for shortest path from start to dest"
  [start dest]
  (loop [queue [{:node start :priority 0 :path []}]
         visited []]
    (let [curr-node (first queue)]
      (if (= dest (:node curr-node))
        (:path curr-node)
        (let [neighbors (get-unvisited-neighbors (:node curr-node) visited)
              neighbor-entries (vec (map (partial build-entry curr-node dest) neighbors))
              new-queue (sort-by :priority (concat (rest queue) neighbor-entries))]
          (recur new-queue
                 (vec (concat visited (vec neighbors) (vector (:node curr-node))))))))))

(defn distance-to-path
  "Return the shortest path number of steps to reach the end of the given path"
  [input]
  (let [dest (walk-path input)
        path (search [0 0] dest)]
    (count path)))

(defn- make-dist-entry [node] {:node node :distance (node-distance [0 0] node)})

(defn max-distance
  "Return the furthest distance on the path"
  [input]
  (let [nodes (generate-path input)
        dis-nodes (distinct nodes)
        distances (->> dis-nodes
                    (map make-dist-entry)
                    (sort-by :distance)
                    reverse
                    (take 5))
        search-dist (->> distances
                      (map :node)
                      (map (partial search [0 0]))
                      (map count))]
    (apply max search-dist)))

(defn -main
  "AOC Day 11 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (println "Part 1:" (distance-to-path input))
      (println "Part 2:" (max-distance input)))))
