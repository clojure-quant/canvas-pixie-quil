(ns comp.button
  (:require
    [clojure.reader :refer [read-string]]
    [clojure.spec.alpha :as s]
    [clojure.string :refer [trim] :as string]
    [goog.object :as gobj]
    [goog.functions :as gfun]
    [comp.mui :as mui]
    [reagent.core :as r]))


(defn confirming-delete-button [{:keys [on-delete tooltip confirm-tooltip]}]
      (r/with-let [timeout  10000
                   clicked? (r/atom false)
                   timeout* (r/atom nil)]
                  [:span
                   [mui/tooltip {:style     {:color :yellow}
                                 :title     (or tooltip "")
                                 :placement "top"}
                    [mui/icon-button
                     {:on-click #(if @clicked?
                                   (do
                                     (js/clearTimeout @timeout*)
                                     (reset! clicked? false)
                                     (on-delete %))
                                   (do
                                     (reset! timeout*
                                             (js/setTimeout
                                               (fn []
                                                 (reset! clicked? false)) timeout))
                                     (reset! clicked? true)))}
                     (if @clicked?
                       [mui/icon "delete_forever"]
                       [mui/icon "delete"])]]
                   (when @clicked?
                     [mui/typography {:style {:display :inline}
                                      :color :error}
                      confirm-tooltip])]))