(ns comp.notification
  (:require
            [clojure.reader :refer [read-string]]
            [clojure.spec.alpha :as s]
            [clojure.string :refer [trim] :as string]
            [goog.object :as gobj]
            [comp.mui :as mui]
            [reagent.core :as r]))


(defn notification [{:keys [notification on-close]}]
      [mui/snackbar {:key (gensym)
                     :auto-hide-duration 5000
                     :open true
                     :anchor-origin {:vertical "top"
                                     :horizontal "right"}
                     :on-close on-close}
       [mui/snackbar-content {:message (:message notification)
                              :action (r/as-element
                                        [mui/icon-button {:key "close"
                                                          :on-click on-close
                                                          :color "secondary"}
                                         (if (:success? notification)
                                           [mui/icon "done"]
                                           [mui/icon "warning"])])}]])

;;; Transitions ;;;

(defn slide [props]
      [mui/slide props])

(defn fade [props]
      [mui/fade props])

(defn zoom [props]
      [mui/zoom props])

(defn grow [props]
      [mui/grow props])



(defn full-screen-dialog [{:keys [open? title on-close close-label top-actions
                                  bottom-actions]}
                          & contents]
      [mui/dialog {:open                 open?
                   :full-screen          true
                   :Transition-component (r/reactify-component slide)
                   :Transition-props     {:direction "up"}
                   :on-close             on-close
                   :Paper-Props          {:style {:background-color mui/gray1}}}

       ;; Top bar
       [mui/mui-theme-provider {:theme mui/jyu-theme-dark}
        (into
          [mui/dialog-actions {:style
                               {:margin           0
                                :padding-right    "0.5em"
                                :background-color mui/primary}}
           [mui/dialog-title {:style {:flex-grow 1}}
            (or title "")]]
          top-actions)]

       ;; Content
       (into [mui/dialog-content {:style {:padding 8}}]
             contents)

       ;; Bottom bar
       [mui/mui-theme-provider {:theme mui/jyu-theme-dark}
        (conj
          (into
            [mui/dialog-actions
             {:style {:margin           0
                      :background-color mui/secondary2}}]
            bottom-actions)
          [mui/button {:on-click on-close}
           close-label])]])


(defn expansion-panel [{:keys [label]} & children]
      [mui/expansion-panel {:style {:margin-top "1em"}}
       [mui/expansion-panel-summary {:expand-icon (r/as-element
                                                    [mui/icon "expand_more"])}
        [mui/typography {:color "primary"
                         :variant "button"}
         label]]
       (into [mui/expansion-panel-details]
             children)])


