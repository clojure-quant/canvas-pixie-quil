(ns comp.input
  (:require [thi.ng.strf.core :as f]
              ))

(defn atom-input
      "textbox that is bound to an external atom"
      [value]
      [:input {:type "text"
               :value @value
               :on-change #(reset! value (-> % .-target .-value))
               }])


(defn int-input
  "textbox that is bound to an external atom, integer value"
  [value]
  [:input {:type "text"
           :value @value
           :on-change #(reset! value (js/parseInt (-> % .-target  .-value)))
           }])


(defn bool-input
  "textbox that is bound to an external atom, integer value"
  [value]
  [:input {:type "checkbox"
           :checked @value
           :on-change #(reset! value (-> % .-target  .-checked))
           }])



; COMBOBOX 1

(defn set-atom [value-atom event]
 (let [value (.. event -target -value)
       xx (println "combo value is: " value)
       xx (println (.. event -target -selectedIndex))]
   (reset! value-atom value)))

(defn atom-selector
      "combobox that is bound to an external atom.
      list is supplied"
      [value-atom list]
      [:select {:on-change #(set-atom value-atom %)          ;  #(reset! value-atom (.. % -target -value))
                :value @value-atom}
       (map-indexed (fn [idx item] [:option {:key idx :value item} item]  ) list)
       ]
      )


; COMBOBOX 2

(defn set-atom2 [value-atom index-atom action event]
      (let [value (.. event -target -value)
            index (.. event -target -selectedIndex)
            xx (println "combo value is: " value)   ]
        (reset! value-atom value)
        (reset! index-atom index)
        (action value)))

(defn atom-selector2
      "combobox that is bound to an external atom.
      list is supplied"
      [value-atom index-atom list action]
      [:select {:on-change #(set-atom2 value-atom index-atom action %)          ;  #(reset! value-atom (.. % -target -value))
                :value @value-atom}
       (map-indexed (fn [idx item] [:option {:key idx :value item} item]  ) list)
       ]
      )

(defn go-next [value-atom index-atom list action]
      (let [new-index (inc @index-atom)
            new-index (if (= new-index (count list)) 0 new-index)
            new-value (nth list new-index)]
        (reset! value-atom new-value)
        (reset! index-atom new-index)
        (action @value-atom)
        )
      )


; ROUND NUMBER

(defn round-number
      [number]
      (if (nil? number) "" (f/format  [(f/float 0)] number)))

(defn round-number-digits
      [digits number]
      (if (nil? number) "" (f/format  [(f/float digits)] number)))
