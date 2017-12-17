(ns day17.core
  "AOC 2017 Day 17" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn next-spinlock-value
  "Find the next value after the give number of cycles"
  [^long step-size num-cycles]
  (loop [cycles (range num-cycles)
         curr-pos 0
         buffer (vector 0)]
    (if (empty? cycles)
      (get buffer (inc curr-pos))
      (let [len (count buffer)
            ^long new-pos (mod (+ curr-pos step-size) len)
            front (subvec buffer 0 (inc new-pos))
            back (subvec buffer (inc new-pos))
            ^long new-val (first cycles)
            new-buffer (vec (concat front (vector (inc new-val)) back))]
        (recur (rest cycles) (inc new-pos) new-buffer)))))

(defn -main
  "AOC Day 16 entrypoint"
  [& args]
  (let [step-size 371
        num-cycles 2017]
    (println "Part 1:" (next-spinlock-value step-size num-cycles))))
