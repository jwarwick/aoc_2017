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
               [caught severity] (simulate firewall)]
           (is (= severity 24))))

(deftest part2
         (let [firewall (build test-input)
               delay (min-delay firewall)]
           (is (= delay 10))))
