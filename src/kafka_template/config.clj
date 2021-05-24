(ns kafka-template.config
  (:require [mount.core :as mount]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log]
            [aero.core :refer [read-config]]
            ))

(defn- get-config!
  []
  (-> "config.edn"
      io/resource
      read-config)) 

(mount/defstate config
  :start (get-config!)
  :stop nil)
