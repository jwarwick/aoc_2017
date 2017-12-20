(ns day20.core
  "AOC 2017 Day 20" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn parse-tuple
  "Parse a p=<x,y,z> string"
  [input]
  (let [l (string/split input #"=")
        v (keyword (first l))
        vs (-> (second l)
             string/trim
             (string/replace #"[<>]" "")
             (string/split #","))
        vint (vec (map #(Integer/parseInt %) vs))]
    {v vint}))

(defn parse-particle
  "Parse a single particle string"
  [idx input]
  (let [s (string/split input #">,")
        tuples (into {} (map parse-tuple s))]
    (assoc tuples :idx idx)))

(defn make-particles
  "Parse input string into particle data structure"
  [input]
  (let [s (string/split input #"\n")
        clean-str (map #(string/replace % #"\s+" "") s)
        parts (map-indexed parse-particle clean-str)]
    parts))

(defn compute-position
  "Figure out where the particle is"
  [[p v a t]]
  (+ p
     (* v t)
     (* (/ 1 2) a (* t t))))

(defn abs
  [x]
  (if (< x 0)
    (* -1 x)
    x))

(defn distance-to
  [pos]
  (->> pos
    (map abs)
    (reduce +)))

(defn distance
  "Compute the manhattan distance to the particle at time t"
  [particle t]
  (let [p (:p particle)
        v (:v particle)
        a (:a particle)
        comb (map vector p v a (repeat t))
        pos (map compute-position comb)
        dist (distance-to pos)]
    (assoc particle :time t :distance dist)))

(defn closest
  "Determine closest particle in the long run to <0,0,0>"
  [particles]
  (let [t 10000]
    (->> particles
      (map #(distance % t))
      (sort-by :distance)
      first
      :idx)))

(defn -main
  "AOC Day 20 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)
          particles (make-particles input)]
      (println "Part 1: " (closest particles))
      (println "Part 2: " "TBD"))))
