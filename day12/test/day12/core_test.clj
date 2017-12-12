(ns day12.core-test
  (:require [clojure.test :refer :all]
            [day12.core :refer :all]))

(def input-str
  "0 <-> 2
1 <-> 1
2 <-> 0, 3, 4
3 <-> 2, 4
4 <-> 2, 3, 6
5 <-> 6
6 <-> 4, 5"
  )

(deftest part1
         (let [pipes (build-graph input-str)
               connected (connected-nodes pipes 0)]
           (is (= 6 (count connected)))))
