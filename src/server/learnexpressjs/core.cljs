(ns learnexpressjs.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(defn -main [& args]
  (.log js/console "Hello World!!!"))

(set! *main-cli-fn* -main)
