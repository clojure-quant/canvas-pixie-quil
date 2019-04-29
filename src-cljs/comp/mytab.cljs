(ns comp.mytab
  (:require  [reagent.core :as r]
             [comp.mui :as mui] ))


(defn on-event [e val]
      ; (println "value is: " val  )
      ; (println "e is: " e  )
  val
  )

; (.. % -target -value)

(defn my-tab [& name-data-vector]
      "makes a tab from a vector of str-name hiccup tuples that are in a vector
   example:  [my-tab 'a' [:h1 'A'] 'b' [:h2 'b'] ]
   "
      (let [active-tab (r/atom 0)]
        (fn [& name-data-vector]
          (let [rows (partition 2 name-data-vector)]
                 [:div
                  [mui/app-bar {:position "static "}
                   [mui/tabs {:value     @active-tab
                              :on-change #(reset! active-tab (on-event %1 %2))}
                    (for [row rows]                         ; row is a lazy seq, therfore use nth instead of get
                      [mui/tab {:label (nth row 0)}]
                      )]
                   ]
                  [mui/paper (nth (nth rows @active-tab) 1)]
                  ]
                 )

          )))