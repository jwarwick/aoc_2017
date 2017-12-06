(ns day6.core-test
  (:require [clojure.test :refer :all]
            [day6.core :refer :all]))

(deftest part1
         (is (= 5 (reallocate-count "0 2 7 0"))))
