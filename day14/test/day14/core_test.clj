(ns day14.core-test
  (:require [clojure.test :refer :all]
            [day14.core :refer :all]))

(deftest part1
  (let [input "flqrgnkx"]
    (is (= 8108 (used-squares input)))))
