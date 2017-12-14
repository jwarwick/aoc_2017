(ns day14.core
  "AOC 2017 Day 14" 
  (:require
    [clojure.string :as string]
    [day14.knothash :as knot])
  (:gen-class))

(defn used-squares
  "Calcuate the number of set bits in a 128x128 grid with the given key-string"
  [key-str]
  (let [grid-size 128
        hashes (->> key-str
                 (repeat grid-size)
                 (map-indexed (fn [idx s] (str s "-" idx)))
                 (map knot/dense-hash)
                 (map #(BigInteger. % 16))
                 (map #(.bitCount %))
                 (reduce +)
                 )]
    hashes))

(defn -main
  "AOC Day 13 entrypoint"
  [& args]
  (let [input "hxtvlmkl"
        used (used-squares input)]
    (println "Part 1:" used)
    (println "Part 2: TBD")))
