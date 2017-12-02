(ns day2.core-test
  (:require [clojure.test :refer :all]
            [day2.core :refer :all]))

(deftest part1
         (is (= 18 (checksum "5 1 9 5\n7 5 3\n2 4 6 8"))))

(deftest part2
         (is (= 9 (div-checksum "5 9 2 8\n9 4 7 3\n3 8 6 5"))))
