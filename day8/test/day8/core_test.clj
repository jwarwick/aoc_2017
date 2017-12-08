(ns day8.core-test
  (:require [clojure.test :refer :all]
            [day8.core :refer :all]))

(def sample-input
  "b inc 5 if a > 1
a inc 1 if b < 5
c dec -10 if a >= 1
c inc -20 if c == 10")

(deftest part1
         (let [reg (run sample-input)
               max-val (max-register reg)]
           (is (= 1 max-val))))
