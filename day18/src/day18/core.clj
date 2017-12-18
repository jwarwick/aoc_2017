(ns day18.core
  "AOC 2017 Day 18" 
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

(defn snd [[x] s] (-> s (assoc :last-sound (get-value s x)) inc-pc))
(defn duet-set [[x y] s] (-> s (assoc x (get-value s y)) inc-pc))
(defn add [[x y] s] (let [^long xv (get-value s x) ^long yv (get-value s y)](-> s (assoc x (+ xv yv)) inc-pc)))
(defn mul [[x y] s] (let [^long xv (get-value s x) ^long yv (get-value s y)](-> s (assoc x (* xv yv)) inc-pc)))
(defn duet-mod [[x y] s] (-> s (assoc x (mod (get-value s x) (get-value s y))) inc-pc))
(defn rcv [[x] s] (if (not= 0 (get-value s x)) (inc-pc s -5000) (inc-pc s)))
(defn jgz [[x y] s] (let [^long xv (get-value s x)] (if (> xv 0) (inc-pc s (get-value s y)) (inc-pc s))))

(defn duet-send
  [[x] s]
  (let [^long sc (get s :send-count 0)]
    (-> s
      (update :outbound #(concat %1 (list (get-value s x))))
      (assoc :send-count (inc sc))
      inc-pc)))

(defn duet-receive
  [[x] s]
  (if (empty? (:inbound s))
    (assoc s :blocked true)
    (-> s
      (dissoc s :blocked)
      (assoc x (first (:inbound s)))
      (update :inbound #(rest %))
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
       (= "set" (first args)) duet-set
       (= "mod" (first args)) duet-mod
       :else (ns-resolve `day18.core (symbol (first args))))
     (parse-arg (second args))
     (when (= 3 (count args)) (parse-arg (last args)))]))

(defn make-instruction-part2
  "Parse a string into an instruction, using Part 2 rules"
  [input-str]
  (let [args (-> input-str
               string/trim
               (string/split #"\s+"))]
    [(cond
       (= "set" (first args)) duet-set
       (= "mod" (first args)) duet-mod
       (= "snd" (first args)) duet-send
       (= "rcv" (first args)) duet-receive
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
            (:last-sound state))
          (let [inst (get prg pc)]
            (recur ((first inst) (rest inst) state))))))))

(defn process-finished?
  "Is this process finished (invalid PC or deadlocked)"
  [state id]
  (let [p (get state id)
        ^long pc (:pc p)
        blocked (:blocked p)
        ^long prg-len (:prg-len p)]
    (or (< pc 0)
        (>= pc prg-len)
        blocked)))

(defn finished? [state] (and (process-finished? state 0) (process-finished? state 1)))

(defn step-process
  [id state prg]
  (let [inst (get prg (get-in state [id :pc]))]
    (if (nil? inst)
      state
      ((first inst) (rest inst) (get state id)))))

(defn move-messages
  "Move outbound queue to other process inbound queue"
  [outbound-state inbound-state]
  (if (empty? (:outbound outbound-state))
    [outbound-state inbound-state]
    [(assoc outbound-state :outbound nil)
     (update inbound-state :inbound #(concat %1 (flatten (list (:outbound outbound-state)))))]))

(defn send-count
  "Return the number of messages sent by process 1"
  [input-str]
  (let [prg (->> (string/split input-str #"\n")
              (map make-instruction-part2)
              vec)
        prg-len (count prg)
        ]
    (loop [state {0 {:pc 0 "p" 0 :send-count 0 :outbound nil :inbound nil :prg-len prg-len}
                  1 {:pc 0 "p" 1 :send-count 0 :outbound nil :inbound nil :prg-len prg-len}}]
      (if (finished? state)
        (let [result (get-in state [1 :send-count])]
          result)
        (let [s0 (step-process 0 state prg)
              s1 (step-process 1 state prg)
              [s0-1 s1-1] (move-messages s0 s1)
              [state1 state0] (move-messages s1-1 s0-1)]
          (recur {0 state0 1 state1}))))))

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
      (println "Part 2:" (send-count input)))))
