(ns day5.core-test
  (:require [clojure.test :refer :all]
            [day5.core :refer :all]))

(deftest part1
         (is (= 5 (steps-to-outside "0\n3\n0\n1\n-3\n"))))
