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
  ([start-lengths string-len] (make-hash start-lengths (vec (range 0 string-len)) 0 0))
  ([start-lengths start-string start-pos start-skip]
   (let [string-len (count start-string)]
     (loop [lengths start-lengths
            string start-string
            curr-pos start-pos
            skip-size start-skip]
       (if (empty? lengths)
         [string curr-pos skip-size]
         (let [new-string (step string (first lengths) curr-pos)
               new-pos (mod (+ curr-pos (first lengths) skip-size) string-len)
               new-skip (inc skip-size)]
           (recur (rest lengths) new-string new-pos new-skip)))))))

(defn make-hash-string
  [input-str string-len]
  (let [start-lengths (->> (string/split input-str #"\,")
                  (map string/trim)
                  (map #(Integer/parseInt %)))
        [result curr skip] (make-hash start-lengths string-len)]
    result))

(defn sparse-hash
  "Compute the sparse hash of the given string"
  [input]
  (let [lengths (-> input
                  seq
                  (#(map int %))
                  (concat [17 31 73 47 23])
                  vec)]
    (loop [itr 0
           curr-pos 0
           skip 0
           string (vec (range 256))]
      (if (>= itr 64)
        string
        (let [[new-string new-curr new-skip] (make-hash lengths string curr-pos skip)]
          (recur (inc itr) new-curr new-skip new-string))))))

(defn to-hex [input] (format "%02x" input))

(defn hexify
  [input]
  (->> input
    (map to-hex)
    string/join))

(defn map-xor
  [arr]
  (map #(reduce bit-xor %) arr))

(defn dense-hash
  "Compute the dense hash of the given string"
  [input]
  (->> input
    sparse-hash
    (partition 16)
    map-xor
    hexify))
        
(defn -main
  "AOC Day 10 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (let [string (make-hash-string input 256)
            m (* (first string) (second string))
            dense (dense-hash input)]
        (println "Part 1:" m)
        (println "Part 2:" dense)))))
