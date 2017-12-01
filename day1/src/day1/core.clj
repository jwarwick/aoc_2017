(ns day1.core
  "AOC 2018 Day 1"
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn sum-if-same
  "Add the value to the accumulator if both elts of the list match"
  [acc [f s :as l]]
  (if (= f s)
    (+ acc (Character/digit f 10))
    acc))

(defn inverse-captcha
  "Compute inverse captcha from a seq of integer characters"
  [s]
  (let [shifted (concat (rest s) (list (first s)))
        zipped (map list s shifted)]
    (reduce sum-if-same 0 zipped)))

(defn inverse-captcha-string
  "Compute inverse captcha from a string"
  [s]
  (inverse-captcha (seq s)))

(defn rotate-by
  "Rotate a sequence a number of times"
  [s cnt]
  (if (<= cnt 0)
    s
    (rotate-by (concat (rest s) (list (first s)))
               (dec cnt))))

(defn inverse-rotated-captcha
  "Compute inverse rotated captcha from a seq of integer characters"
  [s]
  (let [shifted (rotate-by s (/ (count s) 2))
        zipped (map list s shifted)]
    (reduce sum-if-same 0 zipped)))

(defn inverse-rotated-captcha-string
  "Compute inverse rotated captcha from a string"
  [s]
  (inverse-rotated-captcha (seq s)))

(defn part1 [s] (inverse-captcha-string s))
(defn part2 [s] (inverse-rotated-captcha-string s))

(defn -main
  "AOC Day 1 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (->> args
      first
      slurp
      string/trim
      ;; part1
      part2
      println)))
