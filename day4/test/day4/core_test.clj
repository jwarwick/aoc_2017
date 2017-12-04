(ns day4.core-test
  (:require [clojure.test :refer :all]
            [day4.core :refer :all]))

(deftest part1
         (is (= true (valid? "aa bb cc dd ee")))
         (is (= false (valid? "aa bb cc dd aa")))
         (is (= true (valid? "aa bb cc dd aaa"))))

(deftest part1-count
         (is (= 2 (count-valid "aa bb cc dd ee\naa bb cc dd aa\naa bb cc dd aaa\n"))))

