(ns day21.core-test
  (:require [clojure.test :refer :all]
            [day21.core :refer :all]))

(def rules 
  "../.# => ##./#../...
.#./..#/### => #..#/..../..../#..#")

(deftest part1-test
         (let [art (fractal-art rules 2)
               cnt (count-pixels art)]
           (is (= 12 cnt))))
