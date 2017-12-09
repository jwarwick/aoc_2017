(ns day9.core-test
  (:require [clojure.test :refer :all]
            [day9.core :refer :all]))

(deftest part1
         (is (= (first (score "{}")) 1))
         (is (= (first (score "{{{}}}")) 6))
         (is (= (first (score "{{},{}}")) 5))
         (is (= (first (score "{{{},{},{{}}}}")) 16))
         (is (= (first (score "{<a>,<a>,<a>,<a>}")) 1))
         (is (= (first (score "{{<ab>},{<ab>},{<ab>},{<ab>}}")) 9))
         (is (= (first (score "{{<!!>},{<!!>},{<!!>},{<!!>}}")) 9))
         (is (= (first (score "{{<a!>},{<a!>},{<a!>},{<ab>}}")) 3)))

(deftest part2
         (is (= (second (score "<>")) 0))
         (is (= (second (score "<random characters>")) 17))
         (is (= (second (score "<<<<>")) 3))
         (is (= (second (score "<{!>}>")) 2))
         (is (= (second (score "<!!>")) 0))
         (is (= (second (score "<!!!>>")) 0))
         (is (= (second (score "<{o\"i!a,<{i<a>")) 10)))
