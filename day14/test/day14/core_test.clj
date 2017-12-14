(ns day14.core-test
  (:require [clojure.test :refer :all]
            [day14.core :refer :all]))

(deftest part1
  (let [input "flqrgnkx"
        hashes (build-hashes input 128)]
    (is (= 8108 (used-squares hashes)))))

(deftest part2
  (let [input "flqrgnkx"
        hashes (build-hashes input 128)]
    (is (= 1242 (count-regions hashes)))))
