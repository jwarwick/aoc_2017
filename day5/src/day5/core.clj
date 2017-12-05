(ns day5.core
  "AOC 2017 Day 5"
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn part1-inc [value] inc)

(defn part2-inc
  "Decrease curr value if value is 3 or more, otherwise increase curr value"
  [value]
  (if (>= value 3)
    dec
    inc))

(defn take-step
  "Move the PC and increment the current value at the PC"
  [update-fn pc jumps]
  (let [offset (nth jumps pc)
        new-pc (+ pc offset)
       new-jumps (update-in jumps [pc] (update-fn offset))]
   [new-pc new-jumps])) 

(defn steps-to-outside
  "Number of steps until jump list is exited"
  [update-fn input]
  (let [jumps-str (string/split input #"\n")
        jumps-vec (->> jumps-str
                    (map #(Integer/parseInt %))
                    vec)
        vec-len (count jumps-vec)]
    (loop [jumps jumps-vec
           pc 0
           cnt 0]
      (if (or (< pc 0) (>= pc vec-len) (> cnt 50000000))
        cnt
        (let [[new-pc new-vec] (take-step update-fn pc jumps)]
          (recur new-vec new-pc (inc cnt)))))))

(defn -main
  "AOC Day 5 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (println "Part 1:" (steps-to-outside part1-inc input))
      (println "Part 2:" (steps-to-outside part2-inc input)))))
