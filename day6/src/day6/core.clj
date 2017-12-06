(ns day6.core
  "AOC 2017 Day 6"
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn max-idx
  "Find the first max value in a map, assuming the keys are 0..n"
  [memory]
  (loop [cnt (count memory)
         max-value -1
         max-idx 0
         curr-idx 0]
    (if (= 0 cnt)
      [max-idx, max-value]
      (let [v (memory curr-idx)]
        (if (> v max-value)
          (recur (dec cnt) v curr-idx (inc curr-idx))
          (recur (dec cnt) max-value max-idx (inc curr-idx)))))))

(defn reallocate
  "Do one memory reallocation pass"
  [memory len]
  (let [[idx value] (max-idx memory)]
    (loop [m (assoc memory idx 0)
           cnt value
           curr-idx (inc idx)]
      (if (<= cnt 0)
        m
        (do
          (let [new-mem (update m (mod curr-idx len) inc)]
            (recur new-mem (dec cnt) (inc curr-idx))))))))

(defn reallocate-count
  "Reallocate memory banks (as a string), return number of cycles required"
  [input-str]
  (let [input-vec (->> (string/split input-str #"\s+")
                    (map #(Integer/parseInt %))
                    vec)
        input-len (count input-vec)
        input-map (zipmap (range 0 input-len) input-vec)]
    (println "Start:" (vals input-map))
    (loop [cnt 0
           memory input-map
           acc []]
      (if-not (= (count (distinct acc)) (count acc))
        (do
          (println "Found dup at step:" cnt)
          (println (last acc))
          [cnt, acc])
        (let [new-mem (reallocate memory input-len)]
          (recur (inc cnt) new-mem (conj acc (vals new-mem))))))))

(defn get-cycle-count
  [target v]
  (let [value-idx (map-indexed vector v)
        value-filt (filter #(= target (second %)) value-idx)
        idxs (map first value-filt)]
    (- (second idxs) (first idxs))))

(defn -main
  "AOC Day 6 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)]
      (let [[cnt, acc] (reallocate-count input)]
        (println "Part 1:" cnt)
        (let [cycles (get-cycle-count (last acc) acc)]
          (println "Part 2:" cycles))))))

