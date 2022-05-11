(defproject pedestal "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.slf4j/slf4j-simple "1.7.28"]
                 [io.pedestal/pedestal.service "0.5.8"]
                 [io.pedestal/pedestal.jetty "0.5.8"]
                 [io.pedestal/pedestal.route "0.5.8"]
                 [hiccup/hiccup "2.0.0-alpha2"]
                 [org.clojure/clojurescript "0.0-2280"]
                 [cljs-ajax "0.2.6"]
                 [binaryage/devtools "1.0.3"]
                 [reagent "1.0.0"]
                 [re-frame "1.2.0"]
                 [day8.re-frame/tracing "0.6.2"]
                 [day8.re-frame/re-frame-10x "1.0.2"]]
  :plugins [[lein-ring "0.8.10"]
            [lein-environ "0.5.0"]
            [lein-cljsbuild "1.0.3"]
            [lein-figwheel "0.5.16"]]
  :repl-options {:init-ns pedestal.core})
