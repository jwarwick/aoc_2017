(ns day13.core
  "AOC 2017 Day 13" 
  (:require
    [clojure.string :as string])
  (:gen-class))

(defn build-entry
  [[depth-str range-str]]
  (let [depth (Integer/parseInt depth-str)
        range (Integer/parseInt range-str)]
  {:depth depth :range range :pos 0 :length (+ range (- range 2))}))

(defn build
  "Build the firewall structure from an input string"
  [input]
  (->> (string/split input #"\n")
    (map #(string/split % #":\s+"))
    (map build-entry)
    (reduce (fn [acc val] (assoc acc (:depth val) val)) {})))

(defn update-severity
  [curr-pos firewall severity]
  (let [layer (get firewall curr-pos nil)]
    (cond
      (nil? layer) [false severity]
      (not= 0 (:pos layer)) [false severity]
      :else
      (do
        [true
         (+ severity (* (:depth layer) (:range layer)))]))))

(defn update-layer
  [layer]
  (update layer :pos #(mod (inc %) (:length layer))))

(defn update-firewall
  [firewall]
  (into {} (for [[k v] firewall]
             [k (update-layer v)])))

(defn simulate
  "Simulate a packet passing through the firewall. Compute the severity of the trip."
  ([fw] (simulate fw (apply max (keys fw)) -1 false))
  ([fw num-layers start-pos stop-on-collision]
   (loop [curr-pos start-pos
          caught false
          severity 0
          firewall fw]
     (if (or (> curr-pos num-layers)
             (and stop-on-collision caught))
       (do
         [caught severity])
       (let [new-pos (inc curr-pos)
             [new-caught updated-severity] (update-severity new-pos firewall severity)
             updated-firewall (update-firewall firewall)]
         (recur new-pos new-caught updated-severity updated-firewall))))))

(defn test-config
  "Check if a given delay can get through all the layers"
  [delay layers-start]
  (loop [layers layers-start]
    (if (empty? layers)
      true
      (let [[depth length] (first layers)]
        (if (= 0 (mod (+ delay depth) length))
          false
          (recur (rest layers)))))))

(defn min-delay
  "Determine the minimum number of picoseconds to delay so that we can get through the firewall with a collision"
  [firewall]
  (let [layers (->> firewall
                 vals
                 (sort-by :length)
                 (map #(list (:depth %) (:length %)))
                 (map #(into [] %)))]
    (loop [delay 0]
      (let [success (test-config delay layers)]
        (if success
          delay
          (recur (inc delay)))))))

(defn -main
  "AOC Day 13 entrypoint"
  [& args]
  (if (empty? args)
    (println "Expected a path to the input file")
    (let [input (->> args
                  first
                  slurp
                  string/trim)
          firewall (build input)
          [caught severity] (simulate firewall)
          delay (min-delay firewall)]
      (println "Part 1:" severity)
      (println "Part 2:" delay))))
