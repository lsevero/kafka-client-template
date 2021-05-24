(ns kafka-template.worker
  (:require
    [clojure.core.async
     :as a
     :refer [<! go-loop]]
    [clojure.tools.logging :as log]))

(defn worker!
  "Execute a function f each time a message is received at the channel.
  Checks if a exception is passed in a channel, if it is f will not be applied and the exception will be logged.
  "
  [channel f]
  (log/debug (str "Started worker with chan: " channel " fn:" f))
  (go-loop []
           (let [msg (<! channel)]
             (if (instance? Throwable msg)
               (log/error msg "Received a Exception through the channel. Fn will not be applied.")
               (f msg)) 
             (recur))))
