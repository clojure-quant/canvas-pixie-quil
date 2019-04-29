(defproject clfinance "0.1.0-SNAPSHOT"
  :description "Finance Data Mining"
  :url "http://example.com/FIXME"

  :dependencies [
                 ;Common
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "0.4.474" :exclusions [org.clojure/tools.reader]]
                 [clojure-future-spec "1.9.0-beta4"]
                 [org.clojure/java.classpath "0.2.3"]
                 [org.clojure/tools.namespace "0.2.11"]

                 [clj-time "0.14.3"]
                 [camel-snake-kebab "0.4.0"]

                 ;Backend
                 [environ "1.1.0"]                          ; Environment variables
                 [funcool/promesa "1.9.0"]                  ; promises
                 [mount "0.1.11"]                           ;start/stop component
                 [com.taoensso/tufte "2.0.1"]               ;performance tracking

                 ; Database
                 [nio "1.0.3"]                              ; SQ-Lite
                 [org.clojure/java.jdbc "0.7.5"]            ; SQ-Lite
                 [org.xerial/sqlite-jdbc "3.7.2"]           ; SQ-Lite
                 [com.novemberain/monger "3.1.0" :exclusions [com.google.guava/guava]] ; mongodb
                 [capacitor "0.6.0"]                        ; InfluxDB
                 [clj-statsd "0.4.0"]                       ; statsd reporting

                 ; CSV parsing
                 [org.clojure/data.csv "0.1.4"]
                 [org.clojars.bmabey/csvlib "0.3.6"]
                 [ultra-csv "0.2.2"]
                 [semantic-csv "0.2.1-alpha1"]


                 ;Data Science
                 ;[tulos/bberg-sdk "3.6.1.0"]; Bloomberg
                 [com.stuartsierra/frequencies "0.1.0"]     ; percentile stats
                 [incanter/incanter-core "1.9.3"]
                 [incanter/incanter-charts "1.9.3"]
                 [proto-repl "0.3.1"]                       ; ProtoRepl
                 [proto-repl-charts "0.3.2"]                ; ProtoRepl

                 [clj-jgit "0.8.8"]

                 ; Web Server
                 [ring "1.7.0"]
                 [ring/ring-core "1.7.0"]
                 [ring/ring-devel "1.7.0"]
                 [ring/ring-jetty-adapter "1.7.0"]          ; needs to match compojure version
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-codec "1.1.1"]
                 [ring-cors "0.1.12"]                       ; CORS requests
                 [compojure "1.6.1"]                        ; Server-Side Routing
                 [metosin/compojure-api "1.1.10"]
                 [hiccup "1.0.5"]                           ; Templating Server/Side
                 [cheshire "5.8.0"]                         ; JSON encoding

                 ;; LOGGING DEPS
                 [org.clojure/tools.logging "0.2.4"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]


                 ; Frontend
                 [org.clojure/clojurescript "1.10.339"]
                 ;[cljsjs/react "16.4.0-0"]
                 ;[cljsjs/react-dom "16.4.0-0"]
                 ;[binaryage/oops "0.6.2"]
                 ;[com.cognitect/transit-cljs "0.8.243"]
                 [hiccups "0.3.0"]
                 ;[cljsjs/jquery "3.2.1-0"]
                 ;[cljsjs/jquery-ui "1.11.4-0"]
                 [thi.ng/strf "0.2.2"]
                 [noencore "0.3.4"]
                 [reagent "0.8.2-SNAPSHOT" :exclusions [cljsjs/react cljsjs/react-dom]] ; reagent has older react references than material-ui
                 [re-frame "0.10.5"]
                 [cljsjs/material-ui "3.2.0-0"]
                 [cljsjs/material-ui-icons "3.0.1-0"]
                 [secretary "1.2.3"]                        ; client-side routing
                 [cljs-ajax "0.7.3"]                        ; AJAX GET/POST Queries
                 [metasoarous/oz "1.4.1"]                   ; Vega Charting Wrapper
                 [quil "3.0.0"]                             ; 2d graphics
                 [re-com "0.8.3"]
                 [cljsjs/pixi "4.7.0-0"]
                 [cljsjs/pixi-sound "1.4.1-0"]
                 [javax.xml.bind/jaxb-api "2.3.0"]
                 [pointslope/remit "0.1.0-SNAPSHOT"]
  ]


  :main ^:skip-aot job.core                                ;
  ; :aot [ job.stats]

  ; source-paths for clojure
  :source-paths ["src" "src-cljs" "test-cljs"  ]

  :min-lein-version "2.7.1"
  :plugins [[lein-ring "0.9.7"]
            [lein-cljsbuild "1.1.5" :exclusions [[org.clojure/clojure]]] ]

  :ring {; config of the lein-ring plugin https://github.com/weavejester/lein-ring
         :handler web.handler/app
         :init    core/my-init
         }

  :cljsbuild {
              :builds [
                       ; Development Build
                       {:id           "dev"
                        :source-paths ["src-client"]
                        :compiler     {:main                 hello-world.app
                                       :verbose              true
                                       :asset-path           "js/compiled/out"
                                       :output-to            "resources/public/js/compiled/example.js"
                                       :output-dir           "resources/public/js/compiled/out"
                                       :source-map-timestamp true
                                       :externs              ["externs.js"] ; needed for highcharts.
                                       ;:preloads             [devtools.preload]
                                       }
                       }

                       ;Production build
                       {:id           "min"
                        :source-paths ["src-client"]
                        :compiler     {:main                 hello-world.app
                                       :asset-path           "js/compiled/out"
                                       :output-to            "resources/public/cljs-out/dev-main.js"
                                       :source-map-timestamp true
                                       :optimizations :advanced
                                       :closure-defines {goog.DEBUG false}
                                       :pretty-print false
                                       :externs              ["externs.js"] ; needed for highcharts.
                                       ;:preloads             [devtools.preload]
                                       }}
                       ]}


  :aliases {"fig"       ["trampoline" "run" "-m" "figwheel.main"]
            "fig:build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:min"   ["run" "-m" "figwheel.main" "-O" "advanced" "-bo" "dev"]
            "fig:test"  ["run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" hello-world.test-runner]}


  :profiles {:dev {
                   ; Figwheel-Main https://figwheel.org
                   ; :dev profile which is enabled by default
                   :dependencies  [[com.bhauman/figwheel-main "0.2.0"]
                                   ;; optional but recommended
                                   [com.bhauman/rebel-readline-cljs "0.1.4"]
                                   ;[proto-repl "0.3.1"]
                                  ]
              }
              ; jar contains only code
              ; UberJar contains code plus resources
              :uberjar {:aot :all}
             }
  )
