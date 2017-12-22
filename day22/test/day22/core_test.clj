(ns day22.core-test
  (:require [clojure.test :refer :all]
            [day22.core :refer :all]))

(def part1-input
  "..#
#..
...")

(deftest part1
    (is (= 5 (infection-count part1-input 7)))
    (is (= 41 (infection-count part1-input 70)))
    (is (= 5587 (infection-count part1-input 10000))))
