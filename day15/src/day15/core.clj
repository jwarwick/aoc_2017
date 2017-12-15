(ns day15.core
  "AOC 2017 Day 15" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(def factor-a (BigInteger. "16807"))
(def factor-b (BigInteger. "48271"))
(def divisor (BigInteger. "2147483647"))
(def low16bits (BigInteger. "ffff" 16))
(def mod-a (BigInteger. "4"))
(def mod-b (BigInteger. "8"))

(defn part1-iterate
  "An attempt to make things faster"
  [a-start b-start]
  (let [samples 40000000
        gen-a (iterate #(rem (* 16807 %) 2147483647) a-start)
        gen-b (iterate #(rem (* 48271 %) 2147483647) b-start)]
    (count (for [[a-val b-val] (map vector (take (inc samples) gen-a) (take (inc samples) gen-b))
                 :when (= (bit-and a-val 0xffff) (bit-and b-val 0xffff))]
             true))))

(defn generate-next-value-iterate
  "Generate the next valid value"
  [factor mod-value curr-start]
  (loop [curr curr-start]
    (let [next-val (rem (* factor curr) 2147483647)]
      (if (= 0 (rem next-val mod-value))
        next-val
        (recur next-val)))))

(defn part2-iterate
  "An attempt to make things faster (for part2)"
  [a-start b-start]
  (let [samples 5000000
        gen-a (iterate (partial generate-next-value-iterate 16807 4) a-start)
        gen-b (iterate (partial generate-next-value-iterate 48271 8) b-start)]
    (count (for [[a-val b-val] (map vector (take (inc samples) gen-a) (take (inc samples) gen-b))
                 :when (= (bit-and a-val 0xffff) (bit-and b-val 0xffff))]
             true))))

(defn count-matches-part1
  "Return number of low order 16-bit matches between two generators"
  [a-start b-start]
  (loop [step 0
         a (BigInteger. (str a-start))
         b (BigInteger. (str b-start))
         matches 0]
    (if (= step 40000000)
      matches
      (let [next-a (.remainder (.multiply factor-a a) divisor)
            low16-a (.and next-a low16bits)
            next-b (.remainder (.multiply factor-b b) divisor)
            low16-b (.and next-b low16bits)
            new-matches (if (= 0 (.compareTo low16-a low16-b))
                          (inc matches)
                          matches)]
        (when (= 0 (mod step 100000))
          (println step next-a next-b new-matches))
       (recur (inc step) next-a next-b new-matches))))) 

(defn generate-next-value
  "Generate the next valid value"
  [curr-start factor mod-value]
  (loop [curr curr-start]
    (let [next-val (.remainder (.multiply factor curr) divisor)]
      (if (= 0 (.remainder next-val mod-value))
        next-val
        (recur next-val)))))

(defn count-matches-part2
  "Return number of low order 16-bit matches between two generators"
  [a-start b-start]
  (loop [step 0
         a (BigInteger. (str a-start))
         b (BigInteger. (str b-start))
         matches 0]
    (if (= step 5000000)
      matches
      (let [next-a (generate-next-value a factor-a mod-a)
            low16-a (.and next-a low16bits)
            next-b (generate-next-value b factor-b mod-b)
            low16-b (.and next-b low16bits)
            new-matches (if (= 0 (.compareTo low16-a low16-b))
                          (inc matches)
                          matches)]
        (when (= 0 (mod step 100000))
          (println step next-a next-b new-matches))
       (recur (inc step) next-a next-b new-matches))))) 

(defn -main
  "AOC Day 15 entrypoint"
  [& args]
  (let [a-start 883
        b-start 879
        part1-matches (part1-iterate a-start b-start)
        part2-matches (part2-iterate a-start b-start)]
    (println "Part 1:" part1-matches)
    (println "Part 2:" part2-matches)))
