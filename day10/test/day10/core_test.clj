(ns day10.core-test
  (:require [clojure.test :refer :all]
            [day10.core :refer :all]))

(def input-str "3, 4, 1, 5")

(deftest part1
         (let [string (make-hash input-str 5)
               m (* (first string) (second string))]
           (is (= m 12))))
