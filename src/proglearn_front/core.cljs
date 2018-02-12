(ns proglearn-front.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [secretary.core :as secretary :refer-macros [defroute]]
            [reagent.core :as rgt]
            [goog.events :as events]
            [goog.dom :as dom]
            [goog.history.EventType :as EventType]
            [proglearn-front.components :as pc]
            [proglearn-front.apicalls :as apis]
            [cljs.core.async :refer [<!]]
            [proglearn-front.semcomponents :as s]
            [proglearn-front.flow :as flow]
            [proglearn-front.state :as st :refer [app-state]])
  (:import goog.History))

(enable-console-print!)

(defroute "/" []
          (rgt/render [pc/parent-comp]
                      (js/document.getElementById "app")))

(defroute "/challenge" []
          (go
            (let [response (<! (apis/testrequest))
                  r (:data response)]
              (st/add-to-state [:lesson] r)
              (flow/load-next-task)
              (rgt/render [pc/parent-comp]
                          (js/document.getElementById "app")))))

(let [h (History.)]
  (events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (doto h (.setEnabled true)))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)