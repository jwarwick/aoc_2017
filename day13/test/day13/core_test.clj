(ns day13.core-test
  (:require [clojure.test :refer :all]
            [day13.core :refer :all]))

(def test-input
  "0: 3
1: 2
4: 4
6: 4")

(deftest part1
         (let [firewall (build test-input)
               severity (simulate firewall)]
           (is (= severity 24))))
