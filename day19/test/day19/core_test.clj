(ns day19.core-test
  (:require [clojure.test :refer :all]
            [day19.core :refer :all]))

(def input
  "     |          
     |  +--+    
     A  |  C    
 F---|----E|--+ 
     |  |  |  D 
     +B-+  +--+ 
")

(deftest part1
         (let [[path steps] (tube-path input)]
           (is (= "ABCDEF" path))))

(deftest part2
         (let [[path steps] (tube-path input)]
           (is (= 38 steps))))

