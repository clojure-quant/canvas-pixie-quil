(ns ^:figwheel-hooks hello-world.app
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]
   [re-frame.core :as re-frame]


   [hello-world.hello :as hello]

   [bongo.config :as config]
   [bongo.routes :as routes]
   [bongo.events :as events]

   ))

(println "This text is printed from src/hello_world/app.cljs. Go ahead and edit it and see reloading in action.")

(defn multiply [a b] (* a b))


(defn get-app-element []
  (gdom/getElement "app"))


(defn mount [el]
    (println "mounting hello-world")
      ;(reagent/render-component [hello/hello-world] el)
      (reagent/render [routes/current-page] el)
      )

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(defn dev-setup []
      (when config/debug?
        (enable-console-print!)
        (println "dev mode")))


(defn init []
      (dev-setup)
      (re-frame/dispatch-sync [::events/initialize-db])
      (re-frame/clear-subscription-cache!)
      (events/xxx)
      (routes/app-routes)
      )

(init)

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

(let [app (get-app-element)]
  (if (nil? app)
      (println "ERROR dom app-element is not present.")
       (println "dom app element is present.")
    ))


;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
