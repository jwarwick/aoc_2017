(ns day18.core
  "AOC 2017 Day 18" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn get-reg
  "Return the value in a register or 0"
  [state r]
  (get state r 0))

(defn set-reg
  "Set a register value"
  [state r v]
  (assoc state r v))

(defn get-value
  "Return the value in a register if input is a string, otherwise the number"
  [state arg]
  (if (string? arg)
    (get-reg state arg)
    arg))

(defn inc-pc
  "Move the program counter"
  ([state] (inc-pc state 1))
  ([state ^long offset] (update state :pc (fn [^long p] (+ offset p)))))

(defn snd [[x] s] (-> s (assoc :last-sound (get-value s x)) inc-pc))
(defn duet-set [[x y] s] (-> s (assoc x (get-value s y)) inc-pc))
(defn add [[x y] s] (let [^long xv (get-value s x) ^long yv (get-value s y)](-> s (assoc x (+ xv yv)) inc-pc)))
(defn mul [[x y] s] (let [^long xv (get-value s x) ^long yv (get-value s y)](-> s (assoc x (* xv yv)) inc-pc)))
(defn duet-mod [[x y] s] (-> s (assoc x (mod (get-value s x) (get-value s y))) inc-pc))
(defn rcv [[x] s] (if (not= 0 (get-value s x)) (inc-pc s -5000) (inc-pc s)))
(defn jgz [[x y] s] (let [^long xv (get-value s x)] (if (> xv 0) (inc-pc s (get-value s y)) (inc-pc s))))

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
       (= "set" (first args)) duet-set
       (= "mod" (first args)) duet-mod
       :else (ns-resolve `day18.core (symbol (first args))))
     (parse-arg (second args))
     (when (= 3 (count args)) (parse-arg (last args)))]))

(defn recovered-frequency
  "Return the first recovered frequency"
  [input-str]
  (let [prg (->> (string/split input-str #"\n")
              (map make-instruction)
              vec)
        prg-len (count prg)
        ]
    (loop [state {:pc 0}]
      (let [^long pc (:pc state)]
        (if (or (< pc 0) (>= pc prg-len))
          (do
            (println "Recovered frequency:" (:last-sound state))
            (println "Final state:" state)
            (:last-sound state))
          (let [inst (get prg pc)]
            (recur ((first inst) (rest inst) state))))))))

(defn -main
  "AOC Day 18 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (println "Part 1:" (recovered-frequency input))
      (println "Part 2: TBD"))))
