(ns gann.core
 (:require  [reagent.core :as r]
            [comp.mui :as mui]
            [comp.button :as cbutton]
            [comp.mytab :as mytab]
            [comp.notification :as n]
          
           
            [gann.xxx]
            ))

(defn main []
  [:div
   [:h1 "Gann"]
   [:ol
     [:li "Alex loves Clojure."]
     [:li "Gann rocks."]
     [:li "Bitcoin is here."]]
     [gann.xxx/game]
     ])




