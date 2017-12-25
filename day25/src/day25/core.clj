(ns day25.core
  "AOC 2017 Day 25"
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn last-value
  "Get the last value (without trailing . or :)"
  [input]
  (-> input
    (string/split #"\s+")
    last
    (string/replace #"[\.:]" "")))

(defn parse-header
  "Parse blueprint header"
  [input]
  (let [s (-> input
            first
            last-value
            keyword)
        di (-> input
            second
            (string/split #"\s+"))
        d (->> di
            (drop 5)
            first
            Integer/parseInt)]
    {:start s :diagnostic-steps d}))

(defn make-actions
  "Make the transitions for a state"
  [input]
  (let [[v m s] (map last-value input)]
    {:value (Integer/parseInt v) :direction (keyword m) :next-state (keyword s)}))

(defn make-rule
  "Make a rule from the input lines"
  [input]
  (let [s (->> input
            first
            last-value
            keyword)
        zero (->> input
               (drop 2)
               make-actions)
        one (->> input
              (drop 6)
              make-actions)
        ]
    {s {:state s 0 zero 1 one}}))
    
(defn parse-blueprint
  "Parse the Turing machine rules"
  [input-str]
  (let [input (->> input-str
                string/split-lines)
        header (parse-header (take 3 input))
        body (->> input
               (drop 3)
               (partition 10 10 [nil]))
        rules (->> body
                (map make-rule)
                (apply merge))]
    (merge header {:rules rules})))

(defn update-idx
  "Update the index, possibly changing its size of the tape"
  [tape ^long idx rule]
  (let [^long new-idx (case (:direction rule)
                        :right (inc idx)
                        :left (dec idx)
                        :else nil)]
    (cond
      (>= new-idx (count tape))
      [(vec (concat tape [0])) new-idx]

      (< new-idx 0)
      [(vec (reverse (concat (reverse tape) [0]))) 0]

      :else [tape new-idx])))

(defn update-machine
  "Update the state of the machine"
  [tape state idx curr-value curr-rule]
  (let [updated-tape (assoc tape idx (:value curr-rule))
        [new-tape new-idx] (update-idx updated-tape idx curr-rule)]
    [new-tape (:next-state curr-rule) new-idx]))

(defn take-step
  "Evaluate the current state of the Turing machine"
  [tape state idx blueprint]
  (let [curr-value (tape idx)
        curr-rule (get-in blueprint [:rules state curr-value])]
    (update-machine tape state idx curr-value curr-rule)))
  
(defn diagnostic
  "Return the diagnostic value for a blueprint"
  [blueprint]
  (let [^long max-steps (:diagnostic-steps blueprint)]
    (loop [step 0
           tape [0]
           state (:start blueprint)
           idx 0]
      (if (= step max-steps)
        (reduce + tape)
        (let [[new-tape new-state new-idx] (take-step tape state idx blueprint)]
          (recur (inc step) new-tape new-state new-idx))))))

(defn -main
  "AOC Day 25 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)
          blueprint (parse-blueprint input)]
      (println "Part 1:" (diagnostic blueprint))
      (println "Part 2:" "TBD"))))
