(ns day12.core
  "AOC 2017 Day 12" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn parse-line
  [input]
  (let [split (string/split input #"\s+" 3)
        node (Integer/parseInt (first split))
        neighbors (->> (string/split (last split) #"\,")
                    (map string/trim)
                    (map #(Integer/parseInt %)))
        ]
    {:node node :neighbors neighbors}))

(defn build-graph
  "Parse the input string into a map of connected nodes"
  [input]
  (let [lines (->> (string/split input #"\n")
                (map string/trim)
                (map parse-line))]
    (reduce (fn [acc val] (assoc acc (:node val) (:neighbors val))) {} lines)))

(defn connected-nodes
  "Return all the nodes connected to a start node"
  [graph start-node]
  (loop [connected []
         queue (list start-node)]
    (if (empty? queue)
      connected
      (let [curr (first queue)
            neighbors (get graph curr [])
            unvisited (->> neighbors
                        (remove (fn [v] (some #(= % v) connected)))
                        (remove (fn [v] (some #(= % v) queue))))]
        (recur (concat connected (list curr)) (concat (rest queue) unvisited))))))

(defn in-a-group?
  [groups val]
  (->> groups
    (map (fn [group] (some #(= % val) group)))
    (some true?)))

(defn groups
  "Return all the unconnected groups in the graph"
  [graph]
  (let [nodes (keys graph)]
    (loop [queue nodes
           groups []]
      (if (empty? queue)
        groups
        (let [curr (first queue)]
          (if (in-a-group? groups curr)
            (recur (rest queue) groups)
            (recur (rest queue) (concat groups (list (connected-nodes graph curr))))))))))

(defn -main
  "AOC Day 12 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)
          pipes (build-graph input)
          connected (connected-nodes pipes 0)
          g (groups pipes)]
      (println "Part 1:" (count connected))
      (println "Part 2:" (count g)))))
