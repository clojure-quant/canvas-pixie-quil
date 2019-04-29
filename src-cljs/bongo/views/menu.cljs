(ns bongo.views.menu
  (:require
    [reagent.core :refer [atom]]

    [comp.mui :as mui]
    ))


(defn menu [name]

  [mui/paper {:square true
              :style  {:background-color mui/secondary
                       ;; :text-align       :center
                       :padding          "1em 2em 1em 2em"}}

   [mui/grid {:container   true
              :align-items :center
              :spacing     16
              :xs 12}

    [mui/grid {:item true :xs 1}
     [mui/typography {:variant :title
                      :style   {:color   :white}}
      name]]

    [mui/grid {:item true :xs 1}
     [mui/typography {:variant :title
                      :style   {:color   :white}}
      [:a {:href "#/help"} "help"]]]

    [mui/grid {:item true :xs 1}
     [mui/typography {:variant :title
                      :style   {:color   :white}}
      [:a {:href "#/gann"} "gann"]]]

   [mui/grid {:item true :xs 1}
     [mui/typography {:variant :title
                      :style   {:color   :white}}
      [:a {:href "#/tictactoe"} "tictactoe"]]]

   [mui/grid {:item true :xs 1}
     [mui/typography {:variant :title
                      :style   {:color   :white}}
      [:a {:href "#/flo"} "flo"]]]


    [mui/grid {:item true :xs 1}
     [mui/typography {:variant :title
                      :style   {:color   :white}}
      [:a {:href "#/contacts"} "contacts"]]]

    ]
   ])
