(ns proglearn-front.flow
  (:require [reagent.core :as rgt]
            [proglearn-front.state :as st :refer [app-state]]))
;;Receives a lesson and directs the application flow
(defn mark-attempt-state
  [x]
  (if x
    (swap! app-state assoc-in [:play :current :check :state] true)
    (swap! app-state assoc-in [:play :current :check :state] false)))

(defn load-next-puz-in-state
  []
  (st/update-state [:play :currindx] inc))

(defn get-curr-indx
  []
  (get-in @app-state [:play :currindx]))

(defn check-ans
  []
  (let [m (get-in @app-state [:play :current :check :marked])
        ans (get-in @app-state [:play :current :puzzle :answer])]
    (= m ans)))
