(ns day23.core
  "AOC 2017 Day 23" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn get-reg
  "Return the value in a register or 0"
  [state r]
  (get state r 0))

(defn get-value
  "Return the value in a register if input is a string, otherwise the number"
  [state arg]
  (if (string? arg)
    (get-reg state arg)
    arg))

(defn inc-pc
  "Move the program counter"
  ([state] (inc-pc state 1))
  ([state ^long offset]
   (update state :pc (fn [^long p] (+ offset p)))))

(defn aoc-set [[x y] s] (-> s (assoc x (get-value s y)) inc-pc))
(defn sub [[x y] s] (let [^long xv (get-value s x) ^long yv (get-value s y)](-> s (assoc x (- xv yv)) inc-pc)))
(defn jnz [[x y] s] (let [^long xv (get-value s x)] (if (not= xv 0) (inc-pc s (get-value s y)) (inc-pc s))))
(defn mul [[x y] s]
  (let [^long xv (get-value s x) ^long yv (get-value s y)]
    (-> s
      (assoc x (* xv yv))
      (update :mul-count inc)
      inc-pc)))

(defn parse-arg
  "Turn string into a number or register"
  [input]
  (try
    (Integer/parseInt input)
    (catch Exception e
      input)))

(defn make-instruction
  "Parse a string into an instruction"
  [input-str]
  (let [args (-> input-str
               string/trim
               (string/split #"\s+"))]
    [(cond
       (= "set" (first args)) aoc-set
       :else (ns-resolve `day23.core (symbol (first args))))
     (parse-arg (second args))
     (when (= 3 (count args)) (parse-arg (last args)))]))

(defn mul-count
  "Return the number of mul instructions executed"
  [input-str]
  (let [prg (->> input-str
              string/split-lines
              (mapv make-instruction))
        prg-len (count prg)]
    (loop [state {:pc 0 :mul-count 0}]
      (let [^long pc (:pc state)]
        (if (or (< pc 0) (>= pc prg-len))
          (do
            (:mul-count state))
          (let [inst (get prg pc)]
            (recur ((first inst) (rest inst) state))))))))

(defn -main
  "AOC Day 23 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (println "Part 1:" (mul-count input))
      (println "Part 2:" "TBD"))))
