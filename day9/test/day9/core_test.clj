(ns day9.core-test
  (:require [clojure.test :refer :all]
            [day9.core :refer :all]))

(deftest part1
         (is (= (score "{}") 1))
         (is (= (score "{{{}}}") 6))
         (is (= (score "{{},{}}") 5))
         (is (= (score "{{{},{},{{}}}}") 16))
         (is (= (score "{<a>,<a>,<a>,<a>}") 1))
         (is (= (score "{{<ab>},{<ab>},{<ab>},{<ab>}}") 9))
         (is (= (score "{{<!!>},{<!!>},{<!!>},{<!!>}}") 9))
         (is (= (score "{{<a!>},{<a!>},{<a!>},{<ab>}}") 3)))
