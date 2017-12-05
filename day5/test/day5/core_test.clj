(ns day5.core-test
  (:require [clojure.test :refer :all]
            [day5.core :refer :all]))

(deftest part1
         (is (= 5 (steps-to-outside part1-inc "0\n3\n0\n1\n-3\n"))))

(deftest part2
         (is (= 10 (steps-to-outside part2-inc "0\n3\n0\n1\n-3\n"))))
