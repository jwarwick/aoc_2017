(ns day4.core
  "AOC 2017 Day 4"
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn valid?
  "Is the given passphrase string valid (ie no duplicate words)"
  [passphrase]
  (let [pass-list (string/split passphrase #"\s+")
        distinct-list (distinct pass-list)]
  (= (count pass-list) (count distinct-list))))

(defn count-valid
  "Number of valid passphrase in input"
  [input]
  (let [phrases (string/split input #"\n")]
    (->> phrases
      (filter valid?)
      count)))

(defn -main
  "AOC Day 4 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (println "Part 1: " (count-valid input)))))
