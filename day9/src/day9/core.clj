(ns day9.core
  "AOC 2017 Day 9"
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn score
  "Find the score of the input string"
  [input]
  (loop [stream input
         depth 0
         total 0
         garbage-count 0
         in-garbage false]
    (let [f (first stream)
          s (second stream)]
    (cond
      (empty? stream) [total garbage-count]
      (and in-garbage (= f \!)) (recur (rest (rest stream)) depth total garbage-count in-garbage)
      (and in-garbage (= f \>)) (recur (rest stream) depth total garbage-count false)
      in-garbage (recur (rest stream) depth total (inc garbage-count) in-garbage)
      (= f \<) (recur (rest stream) depth total garbage-count true)
      (= f \{) (recur (rest stream) (inc depth) total garbage-count in-garbage)
      (= f \}) (recur (rest stream) (dec depth) (+ total depth) garbage-count in-garbage)
      :else (recur (rest stream) depth total garbage-count in-garbage)
      ))))

(defn -main
  "AOC Day 9 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (let [total-score (score input)]
        (println "Part 1:" (first total-score))
        (println "Part 2:" (second total-score))))))
