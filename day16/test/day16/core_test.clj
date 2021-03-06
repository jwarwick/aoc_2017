(ns day16.core-test
  (:require [clojure.test :refer :all]
            [day16.core :refer :all]))

(deftest part1
         (let [input "s1, x3/4, pe/b"]
           (is (= "baedc" (dance input 5)))))

(deftest part2
         (let [input "s1, x3/4, pe/b"]
           (is (= "ceadb" (multi-dance input 5 2)))))
