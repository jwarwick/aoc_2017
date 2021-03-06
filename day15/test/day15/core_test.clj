(ns day15.core-test
  (:require [clojure.test :refer :all]
            [day15.core :refer :all]))

(deftest part1
         (let [a-start 65
               b-start 8921
               matches (part1-iterate a-start b-start)]
           (is (= 588 matches))))

(deftest part2
         (let [a-start 65
               b-start 8921
               matches (part2-iterate a-start b-start)]
           (is (= 309 matches))))
