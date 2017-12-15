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
        ;; matches (count-matches-part1 a-start b-start)
        matches (count-matches-part2 a-start b-start)]
    ;; (println "Part 1:" matches)
    (println "Part 2:" matches)))
