(ns day17.core-test
  (:require [clojure.test :refer :all]
            [day17.core :refer :all]))

(deftest part1
         (let [step-size 3
               num-cycles 2017]
           (is (= 638 (next-spinlock-value step-size num-cycles)))))
