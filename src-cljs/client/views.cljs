(ns client.views
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [hiccups.runtime :as hiccupsrt]
            [goog.string :as gstring]
            [goog.string.format]
            [thi.ng.strf.core :as f]
            [client.state :as state] ))

(defn uptrend? [field element] (> (field element) 0) )

(def uptrend3? (partial uptrend? :chg-p-3) )
(def uptrend6? (partial uptrend? :chg-p-6) )
(def uptrend12? (partial uptrend? :chg-p-12) )





; (inline-tooltip {:month 3 :data-type "up-12"})
