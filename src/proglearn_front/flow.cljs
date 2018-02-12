(ns proglearn-front.flow
  (:require [reagent.core :as rgt]
            [proglearn-front.state :as st :refer [app-state]]))
;;Receives a lesson and directs the application flow

(def current-task (rgt/atom {}))                            ;current task to render on screen

(def task-no (rgt/atom -1))

(defn get-task
  "Get task of certain index from lesson"
  [lesson index]
  (let [cont (:content lesson)]
    (get cont index)))

(defn load-next-task
  "Reads the current task index & loads next task on challenge completion"
  []
  (swap! task-no inc)
  (swap! current-task (fn [] (get-task
                               (get-in @app-state [:lesson])
                               @task-no))))
