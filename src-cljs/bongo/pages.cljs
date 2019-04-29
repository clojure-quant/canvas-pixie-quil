(ns bongo.pages
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.history.Html5History)
  (:require [reagent.core :as reagent]

            ))


(def app-state (reagent/atom {}))



(defn go-to [page]
  (swap! app-state assoc :page page))


