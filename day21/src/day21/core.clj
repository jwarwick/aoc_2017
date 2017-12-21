(ns day21.core
  "AOC 2017 Day 21" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(def start-pattern ".#./..#/###")

(defn count-pixels
  "Number of pixels that are on in a pattern"
  [pattern]
  (reduce + (flatten pattern)))

(defn print-pattern
  "Pretty print a pattern, return the pattern"
  ([pattern] (print-pattern nil pattern))
  ([label pattern]
   (when label
     (println label))
   (loop [p pattern]
     (if (empty? p)
       pattern
       (do
         (println (first p))
         (recur (rest p)))))))

(defn translate-pattern
  "Translate a pattern string into data"
  [pattern]
  (->> (string/split pattern #"/")
    (map #(map (fn [x] (if (= x \.) 0 1)) %))
    (map vec)
    vec))

(defn translate-rule
  "Translate a rule string into data"
  [rule-str]
  (->> (string/split rule-str #" => ")
    (map translate-pattern)))

(defn flip-vertical
  "Flip a pattern around the X axis"
  ([[a1 b1] [a2 b2]] [[a2 b2] [a1 b1]])
  ([[a1 b1 c1] m [a3 b3 c3]] [[a3 b3 c3] m [a1 b1 c1]]))
  
(defn flip-horizontal
  "Flip a pattern around the Y axis"
  ([[a1 b1] [a2 b2]] [[b1 a1] [b2 a2]])
  ([[a1 b1 c1] [a2 b2 c2] [a3 b3 c3]] [[c1 b1 a1] [c2 b2 a2] [c3 b3 a3]]))

(defn rotate-clockwise
  "Rotate a pattern clockwise around the center"
  ([[a1 b1] [a2 b2]] [[a2 a1] [b2 b1]])
  ([[a1 b1 c1] [a2 b2 c2] [a3 b3 c3]] [[a3 a2 a1] [b3 b2 b1] [c3 c2 c1]]))

(defn add-flips
  "Add horizontal and vertical flips of a pattern to a map"
  [pattern v]
  (assoc {}
         pattern v
         (apply flip-vertical pattern) v
         (apply flip-horizontal pattern) v))

(defn mutate-rule
  "Generate flips and rotations of a given rule"
  [acc [pattern v]]
  (let [cw1 (apply rotate-clockwise pattern)
        cw2 (apply rotate-clockwise cw1)
        cw3 (apply rotate-clockwise cw2)
        ]
    (merge acc
           (add-flips pattern v)
           (add-flips cw1 v)
           (add-flips cw2 v)
           (add-flips cw3 v))))

(defn expand-pattern
  "Return the next state for a size 2 or 3 pattern"
  [rules pattern]
  (let [new-pattern (get rules pattern)]
    (when (nil? new-pattern)
      (print-pattern "FAILED TO FIND PATTERN MATCH" pattern))
    new-pattern))

(defn iterate-art
  "Update a whole pattern"
  [rules pattern]
  (let [size (count pattern)
        offset (if (= 0 (mod size 2)) 2 3)
        new-rows (map #(partition offset %) pattern)
        new-cols (partition offset new-rows)
        sub-patterns (map #(apply map vector %) new-cols)
        new-patterns (map #(map (fn [s] (expand-pattern rules s)) %) sub-patterns)
        combined (map #(apply map vector %) new-patterns)
        combflat (map #(map flatten %) combined)
        join (map vec (reduce concat combflat))]
    (print-pattern join)
    join))

(defn fractal-art
  "Generate art via iteration"
  [rule-book steps]
  (let [start (translate-pattern start-pattern)
        rules (->> (string/split rule-book #"\n")
                (map translate-rule)
                (reduce mutate-rule {}))]
    (println "Num rules" (count rules))
    (print-pattern "Start" start)
    (loop [cnt 0
           pattern start]
      (if (= cnt steps)
        pattern
        (do
          (println "Step" cnt)
          (recur (inc cnt) (iterate-art rules pattern)))))))

(defn -main
  "AOC Day 21 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (println "Part 1: " (count-pixels (fractal-art input 5)))
      (println "Part 2: " (count-pixels (fractal-art input 18))))))
