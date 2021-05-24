(ns kafka-template.core
  (:gen-class)
  (:require [kafka-template 
             [worker :refer [worker!]]
             [consumer :refer [consumer!]]
             [producer :refer [producer!]]
             [config :refer [config]]
             ]
            [clojure.core.async
             :as a
             :refer [go-loop >!!]]
            [mount.core :as mount]
            [clojure.tools.logging :as log]
            [cheshire.core :as json]
            ))

(defn consume-messages
  [msg]
  (log/info (str "Received msg: " msg)))

(mount/defstate producer
  :start (producer! (:producer config)))

(mount/defstate consumer
  :start (worker! (consumer! (:consumer config)) consume-messages))

(defn -main
  []
  (mount/start #'config
               #'producer
               #'consumer)
  (loop [n 0]
    (>!! producer (json/generate-string {:message "ok!!"
                                         :n n}))
    (Thread/sleep 1000)
    (recur (inc n)))
  )
