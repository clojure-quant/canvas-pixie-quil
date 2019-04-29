(ns hello-world.hello
      (:require
      ))

;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:text "Hello world!"}))

(defn on-symbol-select [symbol]
      (println "selected: " symbol)
      )

(defn hello-world []
      [:div
       [:h1 (:text @app-state)]
       [:h3 "Edit this in src/hello_world/app.cljs and watch it change!"]
       [:h2 "bongify v3"]
       ])
