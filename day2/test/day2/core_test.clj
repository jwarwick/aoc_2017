(ns day2.core-test
  (:require [clojure.test :refer :all]
            [day2.core :refer :all]))

(deftest part1
    (is (= 18 (checksum "5 1 9 5\n7 5 3\n2 4 6 8"))))
