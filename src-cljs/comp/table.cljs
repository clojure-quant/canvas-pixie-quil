(ns comp.table
  (:require
    [reagent.core :as r]
    [comp.mui :as mui]
  ))


; header is a vector
; 0 - field / hierarchical field def
; 1 - name
; 2 - formatter
; 3 - link formula
; 4 - on-click



(defn get-field-data [header row]
  (let [field (get header 0)
        data (if (vector? field) (get-in row field ) (field row))
        formatter (get header 2)
        text (if (nil? formatter) data (formatter data))
        linker (get header 3)
        href (if (nil? linker) nil (linker row))
        on-click (get header 4)
        ;yy (println "on-click: " on-click)
        ]
    (if (nil? href)  [:span {:on-click #(on-click row) } text]  [:a {:href href} text] )
    ))


(defn my-table
      "material ui table
       headers: vector [field-selector name formatter linker]
       rows: the seq to be displayed as a table"
      [headers rows]
  [mui/table
  ;; Headers
  [mui/table-head
   (into [mui/table-row]
         (for [field  headers]
              [mui/table-cell (get field 1)]))]
  ;; Body
  (into [mui/table-body]
        (for [row rows]
          (into [mui/table-row]
            (for [field headers]
              [mui/table-cell (get-field-data field row)])
            )))])





