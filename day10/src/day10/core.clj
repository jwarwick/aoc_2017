(ns day10.core
  "AOC 2017 Day 10" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn step
  "Modify the string with a length"
  [string length curr-pos]
  (let [string-len (count string)
        dbl (into string string)
        rev (->> dbl
              (#(subvec % curr-pos (+ curr-pos length)))
              reverse
              vec)]
    (if (>= string-len (+ curr-pos length))
      (let [front (subvec string 0 curr-pos)
            back (subvec dbl (+ curr-pos length))
            updated (->> (concat front rev back)
                      (take (count string))
                      vec)]
        updated)
      (let [diff (- string-len curr-pos)
            back (vec (take diff rev))
            front (vec (drop diff rev))
            middle (vec (take (- string-len length) (drop (count front) string)))
            updated (->> (concat front middle back)
                      (take (count string))
                      vec)]
        updated))))

(defn make-hash
  "Hash a string for a given list of lengths"
  [input-str string-len]
  (let [start-lengths (->> (string/split input-str #"\,")
                  (map string/trim)
                  (map #(Integer/parseInt %)))
        start-string (vec (range 0 string-len))]
    (loop [lengths start-lengths
           string start-string
           curr-pos 0
           skip-size 0
           ]
      (if (empty? lengths)
        string
        (recur (rest lengths)
               (step string (first lengths) curr-pos)
               (mod (+ curr-pos (first lengths) skip-size) string-len)
               (inc skip-size))))))

(defn -main
  "AOC Day 10 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (let [string (make-hash input 256)
            m (* (first string) (second string))]
        (println "Part 1:" m)
        (println "Part 2: TBD")))))
