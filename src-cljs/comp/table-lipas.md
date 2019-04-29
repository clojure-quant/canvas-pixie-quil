


(defn table [{:keys [headers items on-select key-fn sort-fn sort-asc? sort-cmp
                     action-icon hide-action-btn?]
              :or   {sort-cmp         compare
                     sort-asc?        false
                     action-icon      "keyboard_arrow_right"
                     hide-action-btn? false}}]
  (r/with-let [key-fn*   (or key-fn (constantly nil))
               sort-fn*  (r/atom sort-fn)
               sort-asc? (r/atom sort-asc?)]

              [mui/grid {:container true}
               [mui/grid {:item true :xs 12}
                [:div {:style {:overflow-x "auto"}} ; Fixes overflow outside screen

                 [mui/table

                  ;; Head
                  [mui/table-head
                   (into [mui/table-row (when (and on-select (not hide-action-btn?))
                                          [mui/table-cell ""])]
                         (for [[key header] headers]
                           [mui/table-cell {:on-click #(reset! sort-fn* key)}
                            [mui/table-sort-label
                             {:active    (= key @sort-fn*)
                              :direction (if @sort-asc? "asc" "desc")
                              :on-click  #(swap! sort-asc? not)}
                             header]]))]

                  ;; Body
                  [mui/table-body

                   ;; Rows
                   (for [item (if @sort-fn*
                                (sort-by @sort-fn* (if @sort-asc?
                                                     utils/reverse-cmp
                                                     sort-cmp)
                                         items)
                                items)
                         :let [id (or (key-fn* item) (:id item) (:lipas-id item) (gensym))]]
                     [mui/table-row {:key      id
                                     :on-click (when on-select #(on-select item))
                                     :hover    true}
                      (when (and on-select (not hide-action-btn?))
                        [mui/table-cell {:padding "checkbox"}
                         [mui/icon-button {:on-click #(on-select item)}
                          [mui/icon {:color "primary"} action-icon]]])

                      ;; Cells
                      (for [[k _] headers
                            :let  [v (get item k)]]
                        [mui/table-cell {:key (str id k)}
                         [mui/typography {:no-wrap false}
                          (display-value v)]])])]]]]]))