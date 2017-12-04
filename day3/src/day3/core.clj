(ns day3.core
  "AOC 2017 Day 3"
  (:gen-class))

(defn compute-value
  [idx expanded-inner-ring-side last-value]
  (let [shift-idx (inc idx)
        last-safe (or last-value 0)]
    (+ last-safe
       (nth expanded-inner-ring-side (dec shift-idx))
       (nth expanded-inner-ring-side shift-idx)
       (nth expanded-inner-ring-side (inc shift-idx)))))

(defn make-side
  ([inner-ring-side current-ring]
  (let [size (+ 2 (count inner-ring-side))
        expand-inner (-> inner-ring-side
                       reverse
                       (conj 0 0)
                       reverse
                       (conj 0 0)
                       vec)
        expand-last-side (if (empty? current-ring)
                           (vec (repeat size 0))
                           (last current-ring))]
    (loop [acc []]
      (cond (= size (count acc)) acc
            (empty? acc) (recur (vector (last expand-last-side)))
            (= 1 (count acc))
            (let [last-val (nth expand-last-side (- (count expand-last-side) 2))
                  next-val (compute-value (count acc) expand-inner (last acc))]
              (recur (conj acc (+ last-val next-val))))
            (and
              (= 3 (count current-ring))
              (= (- size 2) (count acc)))
            (let [next-val (compute-value (count acc) expand-inner (last acc))]
              (recur (conj acc (+ (nth (first current-ring) 1) next-val))))
            :else
            (let [next-val (compute-value (count acc) expand-inner (last acc))]
              (recur (conj acc next-val))))))))

(def ring-one [[25 1 2] [2 4 5] [5 10 11] [11 23 25]])

(defn- compute-next-ring
  [last-ring]
  (loop [side 0
         acc []]
    (if (= 4 side)
      acc
      (let [next-side (make-side (nth last-ring side) acc)]
        (recur (inc side) (conj acc next-side))))))

(defn next-ring
  [last-ring]
  (let [computed (compute-next-ring last-ring)
        first-side (first computed)
        last-side (last computed)
        last-value (+ (second first-side) (last (last computed)))
        mid-sides (replace computed [1 2])
        update-first (-> first-side
                       (replace (range 1 (count first-side)))
                       reverse
                       vec
                       (conj last-value)
                       reverse
                       vec
                       )
        update-last (-> last-side
                       (replace (range 0 (- (count last-side) 1)))
                       vec
                       (conj last-value))
        updated (-> mid-sides
                  reverse
                  vec
                  (conj update-first)
                  reverse
                  vec
                  (conj update-last)
                  vec)]
    updated))

(defn exceed-value
  "Return the first value computed greater than the target"
  [target]
  (loop [cnt 5
         last-ring ring-one]
    (if (<= cnt 0)
      (do
        (println "Not going to loop more than 5 times....")
        last-ring)
      (let [ring (next-ring last-ring)
            checked-subs (map #(some (fn [x] (> x target)) %) ring)
            exceeded (some true? checked-subs)]
        (println "Ring:" ring)
        (if exceeded
          (do
            (println "Exceeded target value:" target)
            (println "Manually find the value in the output..."))
          (recur (dec cnt) ring))))))

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
  (println "Part 1 - input:" part1-input ", output:" (spiral-distance part1-input))
  (println "Part 2 - input:" part1-input)
  (exceed-value part1-input))
