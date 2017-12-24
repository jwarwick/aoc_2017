(ns day24.core-test
  (:require [clojure.test :refer :all]
            [day24.core :refer :all]))

(def part1-input
  "0/2
2/2
2/3
3/4
3/5
0/1
10/1
9/10")

(deftest part1-test
    (is (= 31 (max-strength part1-input))))
