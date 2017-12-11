(ns day11.core-test
  (:require [clojure.test :refer :all]
            [day11.core :refer :all]))

(deftest part1
         (is (= (distance-to-path "ne,ne,ne") 3))
         (is (= (distance-to-path "ne,ne,sw,sw") 0))
         (is (= (distance-to-path "ne,ne,s,s") 2))
         (is (= (distance-to-path "se,sw,se,sw,sw") 3)))
