(defproject mimir/mimir "0.1.0-SNAPSHOT"
  :description "Mímir is an experimental rule engine written in Clojure"
  :repositories {"sonatype snapshots"
                 "https://oss.sonatype.org/content/repositories/snapshots/"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.flatland/ordered "1.5.1"  :exclusions [org.clojure/clojure]]
                 [log4j/log4j "1.2.16"  :exclusions [org.clojure/clojure]]
                 [org.clojure/tools.logging "0.2.3"  :exclusions [org.clojure/clojure]]
                 [ring/ring-jetty-adapter "1.1.7" :exclusions [org.clojure/clojure]]
                 [org.codehaus.jsr166-mirror/jsr166y "1.7.0"]
                 [compojure "1.1.5"]
                 [de.ubercode.clostache/clostache "1.3.1"]]
  :profiles {:dev {:dependencies [[marginalia "0.7.1" :exclusions [org.clojure/clojure]]
                                  [clojure-lanterna "0.9.2" :exclusions [org.clojure/clojure]]
                                  [org.clojure/tools.trace "0.7.5" :exclusions [org.clojure/clojure]]]}}
  :plugins [[lein-swank "1.4.5"]
            [lein-difftest "2.0.0"]]
  :test-selectors {:default (complement :mk) :mk :mk}
  :main mimir.well
  :min-lein-version "2.0.0")
