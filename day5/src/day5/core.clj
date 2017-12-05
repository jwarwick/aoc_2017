(ns day5.core
  "AOC 2017 Day 5"
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn take-step
  "Move the PC and increment the current value at the PC"
  [pc jumps]
  (let [new-pc (+ pc (nth jumps pc))
       new-jumps (update-in jumps [pc] inc)]
   [new-pc new-jumps])) 

(defn steps-to-outside
  "Number of steps until jump list is exited"
  [input]
  (let [jumps-str (string/split input #"\n")
        jumps-vec (->> jumps-str
                    (map #(Integer/parseInt %))
                    vec)
        vec-len (count jumps-vec)]
    (loop [jumps jumps-vec
           pc 0
           cnt 0]
      (if (or (< pc 0) (>= pc vec-len) (> cnt 1000000))
        cnt
        (let [[new-pc new-vec] (take-step pc jumps)]
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
      (println "Part 1:" (steps-to-outside input)))))
