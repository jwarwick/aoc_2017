(ns day18.core-test
  (:require [clojure.test :refer :all]
            [day18.core :refer :all]))

(def input
  "set a 1
add a 2
mul a a
mod a 5
snd a
set a 0
rcv a
jgz a -1
set a 1
jgz a -2")

(deftest part1
    (is (= 4 (recovered-frequency input))))
