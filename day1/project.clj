(defproject day1 "0.1.0-SNAPSHOT"
  :description "AOC2017: Day1"
  :url "https://adventofcode.com/2017/day/1"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot day1.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
