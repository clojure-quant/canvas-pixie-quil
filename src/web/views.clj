(ns web.views
  (:require [clojure.string :as str]
            [hiccup.core :as h]
            [hiccup.page :as page]
            [ring.util.anti-forgery :as util]
            [ring.util.codec :as codec]

             ))


(defn app-router-page []
  (page/html5
    [:head
      [:title "Seasonator"]

     (page/include-js "http://code.highcharts.com/modules/exporting.js")
     (page/include-js "http://code.highcharts.com/highcharts.js")
     [:script "highcharts.core.main();"]

     ;(page/include-css "https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css")

     ;(page/include-css "/css/styles.css")
     (page/include-css "/css/financials.css")
     (page/include-js "js/compiled/example.js")
     ]

    [:div#root]

    [:script "bongo.core.start();"]
    ))





