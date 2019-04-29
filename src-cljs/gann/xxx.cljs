(ns gann.xxx
  (:require [quil.core :as q :include-macros true]
            [cljsjs.pixi]
            [cljsjs.pixi-sound]
            [reagent.core :as r]
            [gann.engine :as engine]
            [gann.ui :as ui]
            [gann.prizes :as prizes]
            [clojure.walk :refer [postwalk]]
  ))


(def state (volatile! nil))

(defn update-actors [state]
  (update
    state
    :actors
    (fn [actors]
      (postwalk
        (fn [node]
          (if (and (:graphics node) (:update node))
            (let [updated-node ((:update node) node state)]
              (engine/set-graphics-position updated-node)
              updated-node)
            node))
        actors))))


(defn ship-icon []
  (doto (js/PIXI.Graphics.)
    (.beginFill 0x3355ff 0.5)
    (.lineStyle 3 0xFF5500)
    (.moveTo -12.5 -10)
    (.lineTo 12.5 0)
    (.lineTo -12.5 10)
    (.lineTo -12.5 -10)
    (.endFill)))

(defn move-ship [])    

(defn instance [state]
  {:id       :ship
   :type     :player
   :graphics (ship-icon)
   :z-index  1
   :velocity {:y 0 :x 0}
   :width    25
   :height   20
   :radius   10
   :mass     35
   :init     (fn [ship state]
               (assoc ship :x (/ (:width state) 2)
                           :y (/ (:height state) 2)))
   :update move-ship
   })

(defn update-game-state [state]
  (when (engine/started? state)
    (-> state
        ;(update-actors)
        ;(collisions)
        )
        ))

(defn initial-state-map []
  {:score        0
   :total-prizes 5
   :game-state   :stopped
   :vector-field nil
   :force-radius 20
   ;:on-drag      (stage-click-drag add-attractor)
   :update       update-game-state
   ;:background   [(force-field/instance)]
   :foreground   []
   :actors       [(instance state)]
   })

(defn init-state [state]
  (vreset! state (initial-state-map)))

(defn restart []
  (vswap! state assoc :game-state :stopped)
  (engine/clear-stage @state)
  (vswap! state
          (fn [current-state]
            (-> current-state
                (merge (select-keys (initial-state-map) [:game-state :background :actors :foreground :vector-field]))
                (prizes/random-prizes))))
  (engine/init-scene state)
  (engine/init-render-loop state)
  (vswap! state assoc :game-state :started)
  (.start (:ticker @state)))

(defn final-score [{:keys [width height score]}]
  (ui/text-box {:x 20 :y 20 :text (str "Final Score: " score " prizes collected!")}))

(defn restart-button [{:keys [width height score total-prizes]}]
  (ui/button {:label    (if (= score total-prizes) "You Win" "Try Again")
              :x        (- (/ width 2) 100)
              :y        (- (/ height 2) 25)
              :width    200
              :height   50
              :on-click restart}))


(defn canvas [state]
  (r/create-class
    {:component-did-mount
     (engine/init-canvas state ui/help-menu)
     :render
     (fn []
       [:canvas {:width (.-innerWidth js/window) :height (.-innerHeight js/window)}])}))

(defn game []
  [canvas state])


