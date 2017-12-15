(ns day15.core
  "AOC 2017 Day 15" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(def factor-a (BigInteger. "16807"))
(def factor-b (BigInteger. "48271"))
(def divisor (BigInteger. "2147483647"))
(def low16bits (BigInteger. "ffff" 16))

(defn count-matches
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

(defn -main
  "AOC Day 15 entrypoint"
  [& args]
  (let [a-start 883
        b-start 879
        matches (count-matches a-start b-start)]
    (println "Part 1:" matches)
    (println "Part 2: TBD")))
