(ns learnexpressjs.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(def http (nodejs/require "http"))
(def express (nodejs/require "express"))
(def ShareDB (nodejs/require "sharedb"))
(def WebSocket (nodejs/require "ws"))
(def WebSocketJSONStream (nodejs/require "websocket-json-stream"))

(def backend (ShareDB.))

(defn startServer
  []
  ;; Create a web server to serve files and listen to WebSocket connections
  (let [app (express)
        tmp (.use app (.static express "static"))
        server (.createServer http app)
        wss (WebSocket.Server. (clj->js {:server server}))]
    (.on wss "connection" (fn [ws, req] {
                             (let [stream (WebSocketJSONStream. ws)]
                               (.listen backend stream))
                            }))))

;; Create initial document then fire callback
(defn createDoc [callbackfunc]

  )


(defn -main
  []
  (startServer))

(set! *main-cli-fn* -main)
