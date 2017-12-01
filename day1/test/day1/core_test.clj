(ns day1.core-test
  (:require [clojure.test :refer :all]
            [day1.core :refer :all]))

(deftest day1-part1
         (is (= 3 (inverse-captcha-string "1122")))
         (is (= 4 (inverse-captcha-string "1111")))
         (is (= 0 (inverse-captcha-string "1234")))
         (is (= 9 (inverse-captcha-string "91212129")))
         )

(deftest day1-part2
         (is (= 6 (inverse-rotated-captcha-string "1212")))
         (is (= 0 (inverse-rotated-captcha-string "1221")))
         (is (= 4 (inverse-rotated-captcha-string "123425")))
         (is (= 12 (inverse-rotated-captcha-string "123123")))
         (is (= 4 (inverse-rotated-captcha-string "12131415")))
         )
         
         
