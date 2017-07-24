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
    (.on wss "connection" (fn [ws, req]
                             (let [stream (WebSocketJSONStream. ws)]
                               (.listen backend stream))))
    (.listen server 8080)
    (.log js/console "Listening on http://localhost:8080")
    ))

;; Create initial document then fire callback
(defn createDoc [callbackfunc]
  (let [connection (.connect backend)
        doc (.get connection "examples" "counter")]
    (.fetch doc (fn [err]
                  (if (some? err) (throw err))
                  (if (nil? (.-type doc))
                    (.create doc (clj->js {:numClicks 0}) callbackfunc)
                    (callbackfunc)))
    )))


(defn -main
  []
  (createDoc startServer))

(set! *main-cli-fn* -main)
