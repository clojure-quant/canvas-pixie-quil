
(ns client.myutil
  "Utility functions shared on both the client and server."
  (:require
    [clojure.string :refer [replace]]))

;;------------------------------------------------------------------------------
;; Logging
;;------------------------------------------------------------------------------

(defn js-log
  "Log a JavaScript thing."
  [js-thing]
  (js/console.log js-thing))

(defn log
  "Log a Clojure thing."
  [clj-thing]
  (js-log (pr-str clj-thing)))

;;------------------------------------------------------------------------------
;; URL Encoding for Clojuredocs.org
;;------------------------------------------------------------------------------

(def uri-encode js/encodeURIComponent)
