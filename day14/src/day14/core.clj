(ns day14.core
  "AOC 2017 Day 14" 
  (:require
    [clojure.string :as string]
    [day14.knothash :as knot])
  (:gen-class))

(defn build-hashes
  "Build the list of hashes that make up the grid"
  [key-str grid-size]
  (->> key-str
    (repeat grid-size)
    (map-indexed (fn [idx s] (str s "-" idx)))
    (map knot/dense-hash)
    (map #(BigInteger. % 16))))

(defn used-squares
  "Calcuate the number of set bits in a 128x128 grid with the given key-string"
  [hashes]
  (->> hashes
    (map #(.bitCount %))
    (reduce +)))

(defn set-bits
  "Return a list of set bits in the BitInteger"
  [number]
  (for [x (range 128)
        :when (.testBit number x)]
    x))

(defn make-bit-map
  "Make a hashmap of the set bits in the integer"
  [row number]
  (->> number
    (set-bits)
    (map #(vector [row %] true))
    (into {})
    ))

(defn neighbors
  [[r c]]
  (list [(dec r) c]
        [r (inc c)]
        [(inc r) c]
        [r (dec c)]))

(defn remove-region
  [start-node start-bits]
  (loop [queue (list start-node)
         bits start-bits]
    (if (empty? queue)
      bits
      (let [n (first queue)]
        (recur
          (if (contains? bits n)
            (concat (rest queue) (neighbors n))
            (rest queue))
          (dissoc bits n))))))
  
(defn count-regions
  "Count the number of connected components in the inputs"
  [hashes]
  (let [set-bits (->> hashes
                   (map-indexed (fn [idx s] (make-bit-map idx s)))
                   (apply merge)
                   )]
    (loop [region 0
           bits set-bits]
      (if (empty? bits)
        region
        (recur (inc region) (remove-region (first (keys bits)) bits))))))

(defn -main
  "AOC Day 13 entrypoint"
  [& args]
  (let [input "hxtvlmkl"
        hashes (build-hashes input 128)
        used (used-squares hashes)
        regions (count-regions hashes)]
    (println "Part 1:" used)
    (println "Part 2:" regions)))
