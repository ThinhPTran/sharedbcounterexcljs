(ns learnexpressjs.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(def express (nodejs/require "express"))
(def app (express))

(defn -main
  []
  (.log js/console (str "express: " express)))

(set! *main-cli-fn* -main)
