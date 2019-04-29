(ns bongo.views.help
 (:require  [reagent.core :as r]
            [comp.mui :as mui]
            [comp.button :as cbutton]
            [comp.mytab :as mytab]
            [comp.notification :as n]
            ) )


(defn home []
  [:div
   [:h1 "Seasonality Analysis Home"]
   [:ol
    [:li "Seasonality Stats"]
    [:li "Financial Ratios"]
    [:li "Charts"]]])


(def notification-active (r/atom true))

(defn close-notification [&args]
      (println "closing...XX")
      (reset! notification-active false))


(def dialog-active (r/atom false))

(defn open-dialog [&args]
      (reset! dialog-active true))

(defn close-dialog [&args]
      (reset! dialog-active false))




(defn help []
  [:div
   [:h1 "Help"]
   [:ol
     [:li "Alex loves Clojure."]
     [:li "Gann rocks."]
     [:li "Bitcoin is here."]]

   [mytab/my-tab "a" [:h1 "A - LIKE THE A-TEAM"]
                 "b" [:h2 "b is also not so BAD!"]
     ]

   [mui/tooltip {:title "super duper"
                 :on-open (fn [] ( println "tooltip onOpen"))
                 :on-close (fn [] (println "tooltip onClose")) }
    [:h1 "Now get your shit done, please!"] ]


   [:h1 "menu"]
   [cbutton/confirming-delete-button {:tooltip         "you want to delete?"
                                      :confirm-tooltip "are you sure?"
                                      :on-delete open-dialog
                                      }   ]

   (when @notification-active
     [n/notification {:notification {:message "bbk llk lk "
                                     :success? false
                                     }
                      :on-close close-notification}]
     )


   [n/expansion-panel {:label "bongo"}
    [:h1 "The universe is expanding!"]]


   ; dialog gets opened via the confirming delete button above.
   [n/full-screen-dialog
    {:open? @dialog-active
     :title "title"
     :close-label "close"
     :on-close   close-dialog
     :top-actions [:p "top action" ]
     :bottom-actions  [:p "bottom actions"]
     }

    [mui/grid {:container true :spacing 8}
        [:p "Children of the world"]
      ]
     ]


   ])


