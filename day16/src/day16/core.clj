(ns day16.core
  "AOC 2017 Day 16" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn spin
  "Move len elts from the end to the front (right shift)"
  [len dancers]
  (let [end (reverse (take len (reverse dancers)))
        l (concat end dancers)
        result (take (count dancers) l)]
  result))

(defn partner
  "Swap by name"
  [a b dancers]
  (->> dancers
    (map #(if (= a %) nil %))
    (map #(if (= b %) a %))
    (map #(if (= nil %) b %))))

(defn exchange
  "Swap by index"
  [idx-a idx-b dancers]
  (partner (nth dancers idx-a) (nth dancers idx-b) dancers))

(defn make-step
  "Make the partial function call for a step"
  [input-str]
  (let [input (string/trim input-str)
        [cmd-arg1 arg2] (string/split input #"/")
        arg1 (string/join (rest cmd-arg1))]
    (cond
      (= \s (first input)) (partial spin (Integer/parseInt arg1))
      (= \x (first input)) (partial exchange (Integer/parseInt arg1) (Integer/parseInt arg2))
      (= \p (first input)) (partial partner (first arg1) (first arg2))
      :else (str "unknown command" input-str))))

(defn dance
  "Perform the dance specified in the input string"
  [input ^long dancer-count]
  (let [dancers (map char (range 97 (+ 97 dancer-count)))]
    (->> (string/split input #",")
      (map make-step)
      (reduce (fn [acc step] (step acc)) dancers)
      string/join)))

(defn multi-dance
  "Perform a dance multiple times. Will run until we find a cycle..."
  [input ^long dancer-count ^long dances]
  (let [dancers-start (map char (range 97 (+ 97 dancer-count)))
        the-dance (->> (string/split input #",")
                    (map make-step))]
    (loop [cnt 0
           dancers dancers-start
           seen (list dancers-start)]
      (let [new-dancers (reduce (fn [acc step] (step acc)) dancers the-dance)] 
        (if (some #(= new-dancers %) seen)
          (let [r (rem dances (inc cnt))]
            (string/join (nth seen r)))
          (recur (inc cnt) new-dancers (concat seen (list new-dancers))))))))

(defn -main
  "AOC Day 16 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (println "Part 1:" (dance input 16))
      (println "Part 2:" (multi-dance input 16 1000000000)))))
