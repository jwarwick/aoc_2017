(ns day20.core-test
  (:require [clojure.test :refer :all]
            [day20.core :refer :all]))

(def input
  "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>")

(deftest part1
         (let [particles (make-particles input)]
           (is (= 0 (closest particles)))))

(def input2
  "p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>    
p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>
p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>
p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>")

(deftest part2
         (let [particles (make-particles input2)]
           (is (= 1 (simulate particles)))))
