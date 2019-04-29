(ns bongo.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.history.Html5History)
  (:require [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [reagent.core :as reagent]

            [bongo.pages :refer [go-to app-state]]
            [bongo.views.menu  :as menu]
            [bongo.views.help :as help]
            [bongo.views.contacts :as contacts]

            [gann.core]
            [gann.tictac]
            [gann.flo]
            ))


(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen
      EventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))




(defn app-routes []
  (secretary/set-config! :prefix "#")

  (defroute "/" []
            (go-to :home))

  (defroute "/help" []
            (go-to :help))

  (defroute "/contacts" []
           (go-to :contacts))

   (defroute "/gann" []
           (go-to :gann))

   (defroute "/tictactoe" []
           (go-to :tictactoe))

   (defroute "/flo" []
           (go-to :flo))

  (hook-browser-navigation!))


(defn menu-page [name page]
  [:div
    [menu/menu name]
    [page]])



(defmulti current-page #(@app-state :page))

(defmethod current-page :default []
  [menu-page "home" help/home])

(defmethod current-page :home []
  [menu-page "home" help/home])

; DEMO

(defmethod current-page :help []
  [menu-page "help" help/help])

(defmethod current-page :contacts []
  [menu-page "contacts" contacts/contact-list])

; GANN

(defmethod current-page :gann []
  [menu-page "gann" gann.core/main])

(defmethod current-page :tictactoe []
  [menu-page "tictactoe" gann.tictac/tictactoe])

(defmethod current-page :flo []
  [menu-page "flo" gann.flo/demo])




