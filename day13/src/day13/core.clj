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
      (nil? layer) severity
      (not= 0 (:pos layer)) severity
      :else (+ severity (* (:depth layer) (:range layer))))))

(defn update-layer
  [layer]
  (update layer :pos #(mod (inc %) (:length layer))))

(defn update-firewall
  [firewall]
  (into {} (for [[k v] firewall]
             [k (update-layer v)])))

(defn simulate
  "Simulate a packet passing through the firewall. Compute the severity of the trip."
  [fw]
  (let [num-layers (apply max (keys fw))]
    (loop [curr-pos 0
           severity 0
           firewall fw]
      (if (> curr-pos num-layers)
        severity
        (let [updated-severity (update-severity curr-pos firewall severity)
              updated-firewall (update-firewall firewall)]
          (recur (inc curr-pos) updated-severity updated-firewall))))))


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
          severity (simulate firewall)]
      (println "Part 1:" severity)
      (println "Part 2: TBD"))))
