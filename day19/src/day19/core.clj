(ns day19.core
  "AOC 2017 Day 19" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn translate-char
  "Turn puzzle input into symbols"
  [s]
  (cond
    (= s \+) {:type :intersection}
    (= s \|) {:type :vertical}
    (= s \-) {:type :horizontal}
    (= s \space) {:type :empty}
    :else {:type :letter :val s}))

(defn translate
  "Turn line of puzzle into symbols"
  [line]
  (->> line
    seq
    (map translate-char)))

(defn get-node
  "Get the next node in a given direction"
  [[^long r ^long c] direction tubes rows cols]
  (let [[nr nc] (cond
                  (= direction :down) [(inc r) c]
                  (= direction :up) [(dec r) c]
                  (= direction :left) [r (inc c)]
                  (= direction :right) [r (dec c)])
        idx (+ nc (* nr cols))]
    [[nr nc] (get tubes idx nil)]))

(defn invert-direction
  "Return the opposite of a direction"
  [d]
  (cond
    (= d :down) :up
    (= d :up) :down
    (= d :left) :right
    (= d :right) :left))

(defn find-direction
  "Find a direction from an interesection, not the way we came in"
  [[r c] last-direction tubes rows cols]
  (->> (list :up :down :left :right)
    (remove #(= (invert-direction last-direction) %))
    (map #(vector % (get-node [r c] % tubes rows cols)))
    (remove (fn [[d [pos node]]] (or (nil? node) (= :empty (:type node)))))
    first
    first))

(defn get-neighbor
  "Get the next neighbor in the given direction"
  [curr last-direction tubes path rows cols]
  (let [[[nr nc] node] (get-node curr last-direction tubes rows cols)
        t (:type node)]
    (cond
      (or (nil? node)
          (= t :empty)) [nil last-direction path]
      (or (= t :vertical)
          (= t :horizontal)) [[nr nc] last-direction path]
      (= t :letter) [[nr nc] last-direction (concat path (list (:val node)))]
      (= t :intersection) [[nr nc] (find-direction [nr nc] last-direction tubes rows cols) path]
      :else (println "DON'T KNOW WHAT TO DO WITH" nr nc node))))

(defn follow-path
  "Follow the tube map, capture passed letters"
  [start tubes rows cols]
  (loop [curr start
         last-direction :down
         path nil]
    (let [[neighbor direction new-path] (get-neighbor curr last-direction tubes path rows cols)]
      (if (nil? neighbor)
        (string/join path)
        (recur neighbor direction new-path)))))

(defn tube-path
  "Return the letters passed on the path out of the tubes"
  [input]
  (let [lines (string/split input #"\n")
        cols (count (first lines))
        rows (count lines)
        tubes (->> lines
                (map translate)
                flatten
                vec)
        start-col (.indexOf tubes {:type :vertical})
        start [0 start-col]]
    (println rows cols)
    (doseq [x lines]
      (println x)
      )
    (follow-path start tubes rows cols)))

(defn -main
  "AOC Day 19 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp)]
      (println "Part 1:" (tube-path input))
      (println "Part 2:" "TBD"))))
