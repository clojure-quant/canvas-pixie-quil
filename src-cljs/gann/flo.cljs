(ns gann.flo
  (:require [reagent.core :as reagent :refer [atom]]
      [pointslope.remit.events :as pse :refer [emit subscribe]]
      [pointslope.remit.middleware :as psm :refer [event-map-middleware]]
      [reagent.core :as r]
      [gann.components :as c]
      [gann.geometry :as g]
 ))

; http://jonibologna.com/svg-viewbox-and-viewport/
; https://www.mattgreer.org/articles/embedding-svg-into-a-reagent-component/
; https://github.com/awb99/reagent-svg-demo/blob/master/src/cljs/svg/app.cljs
; https://github.com/awb99/tictactoe/blob/master/src/tictactoe/core.cljs
; https://github.com/thi-ng/geom/
; https://github.com/oakes/play-cljs-examples/blob/master/super-koalio/src/super_koalio/core.cljs
; https://reagent-project.github.io/news/any-arguments.html

(def log (.-log js/console))

(defonce points
  (r/atom
   {:p1 (g/point 200 100)
    :p2 (g/point 200 200)
    :p3 (g/point 100 200)
    :c (g/point 250 250)
    :p (g/point 250 300)}))

(defn get-bcr [svg-root]
  (-> svg-root
      r/dom-node
      .getBoundingClientRect))

(defn move-point [svg-root p]
  (fn [x y]
    (let [bcr (get-bcr svg-root)
          xxx (log bcr)  ]
      (swap! points assoc p (g/point (- x (.-left bcr)) (- y (.-top bcr)))))))



(def size 500)

(defn circle [i j]
  [:circle
   {:r 0.35
    :stroke "green"
    :stroke-width 0.12
    :fill "none"
    :cx (+ 0.5 i)
    :cy (+ 0.5 j)}])

(defn cross [i j]
  [:g {:stroke "darkred"
       :stroke-width 0.4
       :stroke-linecap "round"
       :transform
       (str "translate(" (+ 0.5 i) "," (+ 0.5 j) ") "
            "scale(0.3)")}
   [:line {:x1 -1 :y1 -1 :x2 1 :y2 1}]
   [:line {:x1 1 :y1 -1 :x2 -1 :y2 1}]])


(defonce app-state
  (atom {:text "Welcome to tic tac toe"
         :game-status :in-progress}))

;(defn update-status [state]
;  (assoc state :game-status (game-status (:board state))))


(defn select-component
  [fill]
  (fn [fill]
    (let [colors [ "rgb(100,10,70)" "gray" "red" "orange" "yellow" "green" "blue"
                  "aqua" "indigo" "purple" "brown" "black"]]
      (do
        [:div {:class "form-group"}
         [:label {:for "Color"} "Color"]
         [:select {:name "Color"
                   :class "form-control"
                   :value @fill
                   :on-change #(reset! fill (-> % .-target .-value))}
          (for [c colors]
[:option {:value c :key c} c])]]))))



(defn range-component [v min max title]
  [:div {:class "form-group"}
   [:label {:for title} title [:small " [ " @v " ] "]]
   [:input {:type "range"
            :class "form-control"
            :name title
            :key title
            :min min
            :max max
            :value @v
            :on-change #(reset! v (-> % .-target .-value))
            }]])


(defonce app-db (atom {:x 360 :y 200 :radius 50 :fill "black" :clicks 0}))

(defn fib-line [y x width color]
      [:line { :y1 y 
               :y2 y
               :x1 x  
               :x2 (+ x width)  
               :style {:stroke color :stroke-width 2}}])


(defn lines []
  [:g
    [:line {:x1 100 :y1 100 :x2 300 :y2 100 :style {:stroke "blue" :stroke-width 5}}]
    [:line {:x1 100 :y1 200 :x2 350 :y2 200 :style {:stroke "green" :stroke-width 10}}]])


(defn fib-sr [x width color]
  "fibonacci support-resistence"
  (let [fibs [3.618 2.618 1.618 1.0 0.618 0.5 0.362]]
    (fn [x width color] 
      [:g
        (for [y fibs] [fib-line (* 100 y) x width color ])
      ])))
 
 
 ; [:g
 ;     [:line {:x1 100 :y1 100 :x2 500 :y2 100 :style {:stroke "rgb(255,0,0)" :stroke-width 2}}]
 ;     [:line {:x1 100 :y1 200 :x2 500 :y2 200 :style {:stroke "rgb(255,0,0)" :stroke-width 2}}]
 ; ]
 
 (def i 15)
 (def f 5.555)
 (def s "hallo alex")
 ;(def freunde ["alex" "florian" "biene maya" "captain kirk"])
 (def freunde [
    {:alter 33 :land "deutschland" :name "alex" :vater {:name "kemal"} :kinder [ "a" "b" "c"]}
    {:alter 43 :land "panama" :name "florian"}
    {:alter 100 :land "mayastan" :name "biene maya"}
    {:alter 150 :land "usa" :name "lucky luke"}
 ] )
 
(defn drawing [svg-root fill]
  (let [{:keys [p1 p2 p3 p c]} @points]
  [:g
    [:rect {:x 10 :y 10 :width 10 :height 10 :style {:fill @fill}}]
        [:rect {:x 100 :y 50 :width 30 :height 30}]
        [:rect {:x 200 :y 50 :width 30 :height 30 :style {:fill @fill}}]
        [:rect {:x 300 :y 10 :width 30 :height 30 :style {:fill "blue"}}]
        [:rect {:x 150 :y 100 :width 50 :height 50 :style {:fill "green"}}]
        [:circle
          {:r 10
            :stroke "cyan"
            :stroke-width 400
            :fill "none"
            :cx 100
            :cy 150
            :style {:stroke-width 200 }
            }]
        [fib-sr 50 50 @fill]
        [fib-sr 200 50 "rgb(255,0,0)"]  ;red
        [fib-sr 300 50 "blue"]
        [fib-sr 10 30 @fill]
       ;[lines]
        [c/point {:on-drag (move-point svg-root :p1)} p1]
  ]
))


(defn ist-sau-alt [person]
  (let [sau-alt 40]
     (if (>= (:alter person) sau-alt) true false)
))

(defn alters-farbe [person]
  (if (ist-sau-alt person) :red :blue)
)


(defn demo []
 (let [size 700
       view-size (r/atom 400)
       fill (r/atom "green")
      ]
  (fn [] 
    [:center
      [:svg {:x 0 :y 0 
             :width size :height size 
             :view-box (str "0 0 " @view-size " " @view-size)
             :style {:background-color "gray"}
             }
          [drawing (r/current-component) fill]
      ]
      [range-component view-size 0 size "view-size"]
      [select-component fill]
      
      [:h1 (str "View-Size: " @view-size)]
      [:p (str "Addition:" (* 8 (- 100 30 )))]
      [:p (str "Color: " @fill)]
      [:ol (for [freund freunde] 
             [:div 
                [:li {:style {:color (alters-farbe freund)  }} 
                     [:strong (:name freund)] (:alter freund) ]
             ]
                  
              ) ]
      ]
      )))
     


