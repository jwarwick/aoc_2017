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
    (is (= "ABCDEF" (tube-path input))))
