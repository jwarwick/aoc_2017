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
         in-garbage false]
    (let [f (first stream)
          s (second stream)]
    (cond
      (empty? stream) total
      (and in-garbage (= f \!)) (recur (rest (rest stream)) depth total in-garbage)
      (and in-garbage (= f \>)) (recur (rest stream) depth total false)
      in-garbage (recur (rest stream) depth total in-garbage)
      (= f \<) (recur (rest stream) depth total true)
      (= f \{) (recur (rest stream) (inc depth) total in-garbage)
      (= f \}) (recur (rest stream) (dec depth) (+ total depth) in-garbage)
      :else (recur (rest stream) depth total in-garbage)
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
        (println "Part 1:" total-score)
        (println "Part 2: TBD")))))
