(ns kafka-template.core
  (:gen-class)
  (:require [trivial-kafka-async.core :refer [consumer! producer! worker! worker-batch!]]
            [kafka-template 
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

(defn consume-batch
  [msgs]
  (log/info (str "Received a list of msgs: " msgs)))

(mount/defstate producer
  :start (producer! (:producer config)))

(mount/defstate consumer
  :start (worker! (consumer! (:consumer config)) consume-messages))

(mount/defstate another-producer
  :start (producer! (:another-producer config)))

(mount/defstate another-consumer
  :start (worker-batch! (consumer! (:another-consumer config)) 10 consume-batch))

(defn -main
  []
  (mount/start #'config
               #'producer
               #'consumer
               #'another-producer
               #'another-consumer)
  (loop [n 0]
    (>!! producer (json/generate-string {:message "ok!!"
                                         :n n}))
    (>!! another-producer (json/generate-string {:random-number (rand-int 50)
                                                 :n n}))
    (Thread/sleep 1000)
    (recur (inc n))))
