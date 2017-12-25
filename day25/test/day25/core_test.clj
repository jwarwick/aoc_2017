(ns day25.core-test
  (:require [clojure.test :refer :all]
            [day25.core :refer :all]))

(def input
  "Begin in state A.
Perform a diagnostic checksum after 6 steps.

In state A:
  If the current value is 0:
    - Write the value 1.
    - Move one slot to the right.
    - Continue with state B.
  If the current value is 1:
    - Write the value 0.
    - Move one slot to the left.
    - Continue with state B.

In state B:
  If the current value is 0:
    - Write the value 1.
    - Move one slot to the left.
    - Continue with state A.
  If the current value is 1:
    - Write the value 1.
    - Move one slot to the right.
    - Continue with state A.")

(deftest part1-test
         (let [blueprint (parse-blueprint input)]
           (is (= 3 (diagnostic blueprint)))))
