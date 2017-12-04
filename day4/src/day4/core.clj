(ns day4.core
  "AOC 2017 Day 4"
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn valid-anagram?
  "Is the given passphrase string valid (ie no anagram duplicate words)"
  [passphrase]
  (let [pass-list (string/split passphrase #"\s+")
        distinct-list (->> pass-list
                        (map sort)
                        distinct)]
  (= (count pass-list) (count distinct-list))))

(defn valid?
  "Is the given passphrase string valid (ie no duplicate words)"
  [passphrase]
  (let [pass-list (string/split passphrase #"\s+")
        distinct-list (distinct pass-list)]
  (= (count pass-list) (count distinct-list))))

(defn count-valid
  "Number of valid passphrase in input"
  [cmp input]
  (let [phrases (string/split input #"\n")]
    (->> phrases
      (filter cmp)
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
      (println "Part 1:" (count-valid valid? input))
      (println "Part 2:" (count-valid valid-anagram? input)))))
