(ns kafka-template.consumer
  (:require
    [clojure.core.async
     :as a
     :refer [>! go-loop chan]]
    [clojure.tools.logging :as log] 
    )
  (:import
    [java.util Properties]
    [java.time Duration]
    [org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer ConsumerRecord]
    ))

(defn properties-consumer ^Properties
  [cfg]
  (doto (Properties.)
    (.putAll (:properties cfg))))

(defn consumer!
  "Receives a map of a topic and the japa properties.
  Returns a channel that will be populated as a message is received in the kafka poll in a different thread.
  "
  [{:keys [topic properties]}]
  (let [consumer (KafkaConsumer. ^Properties (doto (Properties.)
                                               (.putAll properties)))
        chan-consumer (chan)]

    (.subscribe consumer [topic])
    (go-loop [records []]
             (log/trace (str "records:" records))
             (doseq [^ConsumerRecord record records]
               (try
                 (let [value (.value record)
                       key- (.key record)]
                   (>! chan-consumer {:key key-
                                      :value value})
                   (log/debug (format "Consumed record with key %s and value %s\n" key- value)))
                 (catch Exception e
                   (log/error e (str "Error in kafka consumer topic:" topic)))))
             (recur (seq (.poll consumer (Duration/ofMillis 100)))))
    chan-consumer))

