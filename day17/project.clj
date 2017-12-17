(defproject day17 "0.1.0-SNAPSHOT"
  :description "AOC2017: Day17"
  :url "https://adventofcode.com/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot day17.core
  :target-path "target/%s"
  :global-vars {*warn-on-reflection* true
                *unchecked-math* :warn-on-boxed}
  :profiles {:uberjar {:aot :all}})
