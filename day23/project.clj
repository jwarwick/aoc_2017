(defproject day23 "0.1.0-SNAPSHOT"
  :description "AOC2017: Day23"
  :url "https://adventofcode.com/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot day23.core
  :global-vars {*warn-on-reflection* true
                *unchecked-math* :warn-on-boxed}
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
