(ns day22.core-test
  (:require [clojure.test :refer :all]
            [day22.core :refer :all]))

(def input
  "..#
#..
...")

(deftest part1
         (is (= 5 (infection-count input 7)))
         (is (= 41 (infection-count input 70)))
         (is (= 5587 (infection-count input 10000))))

(deftest part2
         (is (= 26 (enhanced-infection-count input 100)))
         (is (= 2511944 (enhanced-infection-count input 10000000))))
