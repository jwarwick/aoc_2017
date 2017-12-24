(ns day24.core
  "AOC 2017 Day 24" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn find-matches
  "Find all components with the given pin. Also returns list of components with those removed."
  [pin comps]
  (let [groups (group-by (fn [[x y]] (or (= pin x) (= pin y))) comps)]
    (groups true)))

(defn other-pin
  "Return the value of the other end of a component"
  [[x y] pin]
  (if (= x pin) y x))

(declare build-branch)

(defn make-node
  "Make a child node from a parent node"
  [component parent-node comps]
  (let [^long parent-strength (:strength parent-node)
        [^long x ^long y] component]
    (build-branch  {:left-pin (:right-pin parent-node)
                    :right-pin (other-pin component (:right-pin parent-node))
                    :path (concat (:path parent-node) [component])
                    :comps comps
                    :strength (+ parent-strength x y)})))

(defn build-branch
  "Build a branch of the bridge"
  [node]
  (let [child-queue (find-matches (:right-pin node) (:comps node))]
    (if (nil? child-queue)
      {:strength (:strength node) :path (:path node)}
      (map #(make-node % node (remove (fn [x] (= x %)) (:comps node)))
           child-queue))))

(defn build-bridges
  "Build tree of all possible bridges"
  [start-pin comps]
  (build-branch {:left-pin nil
                 :right-pin start-pin
                 :path nil
                 :comps comps
                 :strength 0}))
  
(defn max-strength
  "Determine the strongest bridge that can be built from the given components"
  [input-str]
  (let [comps (->> input-str
                string/split-lines
                (map #(string/split % #"/"))
                (map (fn [[x y]] [(Integer/parseInt x) (Integer/parseInt y)])))
        bridges (build-bridges 0 comps)]
    (->> bridges
      flatten
      (sort-by :strength)
      last
      :strength)))

(defn -main
  "AOC Day 24 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (println "Part 1:" (max-strength input))
      (println "Part 2:" "TBD"))))
