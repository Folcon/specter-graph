(defproject specter-graph "0.1.0-SNAPSHOT"
  :description "A graph navigation library for specter"
  :url "https://github.com/Folcon/specter-graph"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.439"]
                 [com.rpl/specter "1.1.2"]
                 [ubergraph "0.5.2"]
                 [aysylu/loom "1.0.2"]]

  :source-paths ["src/clj" "src/cljc" "src/cljs"]
)
