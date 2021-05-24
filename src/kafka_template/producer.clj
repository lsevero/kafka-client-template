(ns kafka-template.producer
  (:require
    [clojure.core.async
     :as a
     :refer [<! go-loop chan]]
    [clojure.tools.logging :as log] 
    )
  (:import
    [java.util Properties]
    [org.apache.kafka.clients.admin AdminClient NewTopic]
    [org.apache.kafka.clients.producer Callback KafkaProducer ProducerConfig ProducerRecord RecordMetadata]
    [org.apache.kafka.common.errors TopicExistsException]))

(defn properties-producer ^Properties
  [cfg]
  )

(defn producer!
  "Receives a map of a topic and the japa properties.
  Returns a channel that will be consumed and sent each individual message to kafka in a different thread.
  "
  [{:keys [topic properties]}]
  (letfn [(print-ex [e] (log/error e "Failed to deliver message."))
          (print-metadata [^RecordMetadata x]
            (log/debug (format "Produced record to topic %s partition [%d] @ offest %d\n"
                              (.topic x)
                              (.partition x)
                              (.offset x))))]

    (let [producer (KafkaProducer. ^Properties (doto (Properties.)
                                                 (.putAll properties)))
          callback (reify Callback
                     (onCompletion [this metadata exception]
                       (if exception
                         (print-ex exception)
                         (print-metadata metadata))))
          chan-producer (chan)]

      (go-loop []
               (let [record (ProducerRecord. topic (<! chan-producer))]
                 (log/trace "chan-producer received: " record)
                 (doto producer 
                   (.send record callback)
                   (.flush)))
               (recur))
      chan-producer)))
