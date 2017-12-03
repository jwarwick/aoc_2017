(ns day3.core
  "AOC 2017 Day 3"
  (:gen-class))

(defn make-ring
  "Make a list of the numbers in a given ring index"
  [index]
  (let [prev-size (max 0 (inc (* 2 (dec index))))
        size (inc (* 2 index))]
    (range (inc (* prev-size prev-size)) (inc (* size size)))))

(defn make-spiral
  "Return a list rings in the spiral up to the ring with the target number"
  ([target] (make-spiral target 0 1 `()))
  ([target index size acc]
   (let [updated-acc (cons (make-ring size) acc)]
     (if (<= target (* size size))
       (reverse updated-acc)
       (make-spiral target (inc index) (+ 2 size) updated-acc)))))

(defn find-ring
  "Return the 0-indexed ring the target number is contained in"
  ([target] (find-ring target 0 1))
  ([target index size]
   (if (<= target (* size size))
     index
     (find-ring target (inc index) (+ 2 size)))))

(defn spiral-distance
  "Manhattan distance for spiral memory"
  [input]
  (if (= 1 input)
    0
    (let [ring-index (find-ring input)
          size (inc (* 2 ring-index))
          ring (make-ring ring-index)
          ring-extended (conj ring (last ring))
          ring-partition (partition size (dec size) ring-extended)
          side (last (filter #(some (fn [x] (= x input)) %) ring-partition))
          midpoint (nth side (int (/ size 2)))
          dist-mid (Math/abs (- input midpoint))
          ]
      (+ dist-mid ring-index))))

(def part1-input 347991)

(defn -main
  "AOC Day 3 entrypoint"
  [& args]
  (println "Part 1 - input:" part1-input ", output:" (spiral-distance part1-input)))
