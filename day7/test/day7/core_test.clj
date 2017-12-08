(ns day7.core-test
  (:require [clojure.test :refer :all]
            [day7.core :refer :all]))

(def part1-test-input
  "pbga (66)
  xhth (57)
  ebii (61)
  havc (66)
  ktlj (57)
  fwft (72) -> ktlj, cntj, xhth
  qoyq (66)
  padx (45) -> pbga, havc, qoyq
  tknk (41) -> ugml, padx, fwft
  jptl (61)
  ugml (68) -> gyxo, ebii, jptl
  gyxo (61)
  cntj (57)")

(deftest part1
         (let [tree (build-tree part1-test-input)
               r (root tree)]
           (is (= "tknk" r))))

(deftest part2
         (let [tree (build-tree part1-test-input)
               r (root tree)]
           (is (= 60 (corrected-weight tree root)))))
