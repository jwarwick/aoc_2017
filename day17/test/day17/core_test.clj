(ns day17.core-test
  (:require [clojure.test :refer :all]
            [day17.core :refer :all]))

(deftest part1
         (let [step-size 3
               num-cycles 2017
               curr-based (next-spinlock-value step-size num-cycles)]
           (is (= 638 curr-based))))

(deftest part2
         (let [step-size 3
               num-cycles 2017
               zero-based (track-spinlock-zero step-size num-cycles)]
           (is (= 1226 zero-based))))
