(ns day3.core-test
  (:require [clojure.test :refer :all]
            [day3.core :refer :all]))

(deftest part1
         (is (= 0 (spiral-distance 1)))
         (is (= 3 (spiral-distance 12)))
         (is (= 2 (spiral-distance 23)))
         (is (= 31 (spiral-distance 1024))))
