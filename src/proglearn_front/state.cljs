(ns proglearn-front.state
  (:require [reagent.core :as rgt]))

(defonce app-state (rgt/atom {}))

(defn add-to-state
  [keyvec value]
  (let [newstate (update-in @app-state keyvec merge value)]
    (swap! app-state (fn [] newstate))))

(defn update-state
  [keyvec func]
  (let [newstate (update-in @app-state keyvec func)]
    (swap! app-state (fn [] newstate))))
