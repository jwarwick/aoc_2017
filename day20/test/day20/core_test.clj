(ns day20.core-test
  (:require [clojure.test :refer :all]
            [day20.core :refer :all]))

(def input
  "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>")

(deftest part1
         (let [particles (make-particles input)]
           (is (= 0 (closest particles)))))
