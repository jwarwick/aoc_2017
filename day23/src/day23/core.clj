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

(defn print-reg
  "Print registers"
  [state]
  (println (select-keys state [:pc "a" "b" "c" "d" "e" "f" "g" "h"])))

(defn run
  "Run the program"
  ([input-str] (run input-str false))
  ([input-str debug]
   (let [prg (->> input-str
               string/split-lines
               (mapv make-instruction))
         prg-len (count prg)
         debug-map (if debug {"a" 0} {"a" 1})]
     (loop [state (merge debug-map {:pc 0 :mul-count 0})]
       (let [^long pc (:pc state)]
         ;; (print-reg state)
         (if (or (< pc 0) (>= pc prg-len))
           state
           (let [inst (get prg pc)]
             (recur ((first inst) (rest inst) state)))))))))

(defn part1
  "Count the number of mul instructions"
  [input]
  (:mul-count (run input true)))

(defn part2
  "Return the value of the h register"
  [input]
  (:h (run input)))

(defn root-1 [x]
    (inc (long (Math/sqrt x))))

(defn range-1 [x]
  (range 2 (root-1 x)))

(defn filter-1 [x]
  (filter #(zero? (rem x %))
        (range-1 x)))

(defn is-prime [x]
  (nil? (first (filter-1 x))))

(defn part2-native
  "Clojure implementation of the part2 problem. How many numbers are not prime in the range 106700 to 123700 with a step size of 17."
  []
  (->> (range 106700 (inc 123700) 17)
    (remove is-prime)
    count))

(defn -main
  "AOC Day 23 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (println "Part 1:" (part1 input))
      (println "Part 2:" (part2-native)))))
