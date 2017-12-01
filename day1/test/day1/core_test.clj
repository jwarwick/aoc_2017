(ns day1.core-test
  (:require [clojure.test :refer :all]
            [day1.core :refer :all]))

(deftest day1-part1
         (is (= 3 (inverse-captcha-string "1122")))
         (is (= 4 (inverse-captcha-string "1111")))
         (is (= 0 (inverse-captcha-string "1234")))
         (is (= 9 (inverse-captcha-string "91212129")))
         )
         
