(ns web.handler
  (:require
    [schema.core :as s]

    [ring.util.response :as response]
    [ring.util.http-response :refer :all]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
    [ring.middleware.reload :refer [wrap-reload]]
    [ring.middleware.cors :refer [wrap-cors]]

    [compojure.core :refer :all] ; [compojure.core :refer [defroutes routes]]
    [compojure.route :as route]
    [compojure.api.sweet :as sweet]
    [cheshire.core :refer :all]

    [web.views :as myviews]))

(defroutes app-routes

   ; (route/resources "/" {:root "public"})
   ;; NOTE: this will deliver your index.html
   (GET "/" [] (-> (response/resource-response "index.html" {:root "public"})
                   (response/content-type "text/html")))

  ; reagent apps
  (GET "/approuter" [] (myviews/app-router-page))

  ; Api

  (sweet/api
    (sweet/context "/api" []
      :tags ["api"]

      ; DEMO

      (sweet/GET "/hello" []
        :query-params [name :- s/Str , {age :- s/Int nil}]
        (if (nil? age) (ring.util.http-response/ok {:message (str "Hello, " name)})
                       (ring.util.http-response/ok {:message (str "Hello, " name " - you look good for " age " years!")})))

      (sweet/GET "/plus" []
        :return {:result Long}
        :query-params [x :- Long, y :- Long]
        :summary "adds two numbers together"
        (ring.util.http-response/ok {:result (+ x y)}))

      ; UNIVERSE

      ))

  (route/not-found "<h1> Sorry! Page not found.</h1>"))


 (def app1 (wrap-defaults app-routes site-defaults) )

(def app (wrap-cors app1 :access-control-allow-origin [#".*"]
           :access-control-allow-methods [:get :put :post :delete]))

;(def app (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)))

;; NOTE: wrap reload isn't needed when the clj sources are watched by figwheel
;; but it's very good to know about
(def dev-app (wrap-reload (wrap-defaults #'app-routes site-defaults)))


