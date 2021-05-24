(defproject kafka-template "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [cheshire "5.8.1"]
                 [mount "0.1.16"]
                 [aero "1.1.6"]
                 [com.damballa/abracad "0.4.13"]
                 [org.apache.kafka/kafka-clients "2.1.0"]
                 [org.clojure/core.async "1.3.618"]
                 [org.clojure/tools.logging "1.1.0"]
                 ]

  :main kafka-template.core
  
  :profiles {:dev {:source-paths ["src" "dev" "test"]
                   :resource-paths ["resources"
                                    "config/dev"]
                   :global-vars {*warn-on-reflection* true}
                   :plugins [[lein-ring "0.12.5"]
                             [refactor-nrepl "2.4.0"]
                             [lein-localrepo "0.5.4"]
                             [cider/cider-nrepl "0.25.4"]
                             [jonase/eastwood "0.3.6"]
                             [lein-ancient "0.6.15"]
                             [lein-cloverage "1.1.2"]]
                   :dependencies [[org.apache.logging.log4j/log4j-api "2.13.3"]
                                  [org.apache.logging.log4j/log4j-core "2.13.3"]
                                  [org.apache.logging.log4j/log4j-slf4j-impl "2.13.3"]
                                  [com.clojure-goes-fast/clj-java-decompiler "0.3.0"]
                                  ]
                   }
             :test {:source-paths ["src" "dev" "test"]
                    :global-vars {*warn-on-reflection* true}
                    :resource-paths ["resources"
                                     "config/test"] 
                    :plugins [[lein-ring "0.12.5"]
                              [refactor-nrepl "2.4.0"]
                              [lein-localrepo "0.5.4"]
                              [cider/cider-nrepl "0.25.4"]
                              [jonase/eastwood "0.3.6"]
                              [lein-ancient "0.6.15"]
                              [lein-cloverage "1.1.2"]]
                    :dependencies [[com.clojure-goes-fast/clj-java-decompiler "0.3.0"]
                                   ]
                    }
             :staging {:source-paths ["src"]
                       :resource-paths ["resources"
                                        "config/stag"]
                       :dependencies [[org.apache.logging.log4j/log4j-api "2.13.3"]
                                      [org.apache.logging.log4j/log4j-core "2.13.3"]
                                      [org.apache.logging.log4j/log4j-slf4j-impl "2.13.3"]]
                       }
             :development {:source-paths ["src"]
                           :resource-paths ["resources"
                                            "config/dev"]
                           :dependencies [[org.apache.logging.log4j/log4j-api "2.13.3"]
                                          [org.apache.logging.log4j/log4j-core "2.13.3"]
                                          [org.apache.logging.log4j/log4j-slf4j-impl "2.13.3"]]
                           }
             :sandbox {:source-paths ["src"]
                       :resource-paths ["resources"
                                        "config/sandbox"]
                       :dependencies [[org.apache.logging.log4j/log4j-api "2.13.3"]
                                      [org.apache.logging.log4j/log4j-core "2.13.3"]
                                      [org.apache.logging.log4j/log4j-slf4j-impl "2.13.3"]]
                       }
             :production {:source-paths ["src"]
                          :resource-paths ["resources"
                                           "config/prod"]
                          :dependencies [[org.apache.logging.log4j/log4j-api "2.13.3"]
                                         [org.apache.logging.log4j/log4j-core "2.13.3"]
                                         [org.apache.logging.log4j/log4j-slf4j-impl "2.13.3"]]
                          }
             :uberjar {:aot :all}
             } 
  :repl-options {:init-ns kafka-template.core})
