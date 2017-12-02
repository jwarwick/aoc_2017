(ns day2.core
  "AOC 2017 Day 2"
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn checksum
  "Compute checksum of spreadsheet"
  [input]
  (let [lines (string/split input #"\n")]
    (->> lines
      (map #(string/split % #"\s+"))
      (map #(map (fn [x] (Integer. x)) %))
      (map sort)
      (map #(- (last %) (first %)))
      (reduce +))))

(defn div-checksum
  "Compute division-based checksum of spreadsheet"
  [input]
  (let [lines (string/split input #"\n")]
    (->> lines
      (map #(string/split % #"\s+"))
      (map #(map (fn [x] (Integer. x)) %))
      (map #(for [x % y %] (list x y)))
      (map #(remove (fn [[x y]] (= x y)) %))
      (map #(filter (fn [[x y]] (= 0 (mod x y))) %))
      (map first)
      (map (fn [[x y]] (/ x y)))
      (reduce +))))

(defn -main
  "AOC Day 2 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (println "Part 1: " (checksum input))
      (println "Part 2: " (div-checksum input)))))
