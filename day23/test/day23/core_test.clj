(ns day23.core-test
  (:require [clojure.test :refer :all]
            [day23.core :refer :all]))

(deftest part1-test
    (is (= 4225 (part1 (slurp "input.txt")))))

