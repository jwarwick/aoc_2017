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
      (let [len (count buffer)]
        (get buffer (mod (inc curr-pos) len)))
      (let [len (count buffer)
            ^long new-pos (mod (+ curr-pos step-size) len)
            front (subvec buffer 0 (inc new-pos))
            back (subvec buffer (inc new-pos))
            ^long new-val (first cycles)
            new-buffer (vec (concat front (vector (inc new-val)) back))]
        (recur (rest cycles) (inc new-pos) new-buffer)))))

;; (defn track-spinlock-zero
;;   "Don't keep a buffer, just track when we would write to index 1"
;;   [^long step-size num-cycles]
;;   (loop [cycles (range num-cycles)
;;          curr-pos 0
;;          val-idx1 0]
;;     (if (empty? cycles)
;;       val-idx1
;;       (let [^long new-val (first cycles)
;;             ^long new-pos (mod (+ curr-pos step-size) (inc new-val))]
;;       (recur (rest cycles) (inc new-pos) (if (= 0 new-pos) (inc new-val) val-idx1))))))
      
(defn spinlock
  "Test if we write to idx 0"
  [^long step-size [^long curr-pos ^long val-idx1] ^long new-val]
  (let [^long new-pos (mod (+ curr-pos step-size) (inc new-val))]
    [(inc new-pos) (if (= 0 new-pos) (inc new-val) val-idx1)]))
      
(defn track-spinlock-zero
  "Don't keep a buffer, just track when we would write to index 1"
  [^long step-size ^long num-cycles]
  (last (reduce (partial spinlock step-size) [0 0] (range (inc num-cycles)))))

(defn -main
  "AOC Day 16 entrypoint"
  [& args]
  (let [step-size 371
        num-cycles 2017
        num-cycles-part2 50000000
        curr-based (next-spinlock-value step-size num-cycles)
        zero-based (track-spinlock-zero step-size num-cycles-part2)]
    (println "Part 1:" curr-based)
    (println "Part 2:" zero-based)))
