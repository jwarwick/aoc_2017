(ns day22.core
  "AOC 2017 Day 22" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn infected?
  "Is this node infected"
  [infections node]
  (contains? infections node))

(defn turn-right
  "Turn right from current direction"
  [dir]
  (case dir
    :up :right
    :right :down
    :down :left
    :left :up))

(defn turn-left
  "Turn left from current direction"
  [dir]
  (case dir
    :up :left
    :left :down
    :down :right
    :right :up))

(defn turn
  "Pick a new heading based on the infection state"
  [infected dir]
  (if infected (turn-right dir) (turn-left dir)))

(defn move
  "Move in the given direction"
  [[^long x ^long y] dir]
  (case dir
    :up [x (dec y)]
    :right [(inc x) y]
    :down [x (inc y)]
    :left [(dec x) y]))

(defn infection-count
  "Return the number of bursts that caused a new infection"
  [input-str steps]
  (let [input (->> input-str
                string/split-lines
                (map vec)
                (map-indexed (fn [x row] (map-indexed (fn [y v] [[y x] v]) row))))
        cx (int (Math/floor (/ (count (first input)) 2)))
        cy (int (Math/floor (/ (count input) 2)))
        infect-map (->> input
                     (apply concat)
                     (filter #(= \# (last %)))
                     (into {}))]
    (loop [step 0
           curr [cx cy]
           direction :up
           infections infect-map
           infection-count 0]
      (if (= step steps)
        infection-count
        (let [infected (infected? infections curr)
              new-direction (turn infected direction)
              new-infections (if infected (dissoc infections curr) (assoc infections curr \#))
              new-infection-count (if infected infection-count (inc infection-count))
              new-pos (move curr new-direction)]
          (recur (inc step) new-pos new-direction new-infections new-infection-count))))))

(defn -main
  "AOC Day 22 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (println "Part 1: " (infection-count input 10000))
      (println "Part 2: " "TBD"))))
