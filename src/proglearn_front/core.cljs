(ns proglearn-front.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [secretary.core :as secretary :refer-macros [defroute]]
            [reagent.core :as rgt]
            [goog.events :as events]
            [goog.dom :as dom]
            [goog.history.EventType :as EventType]
            [proglearn-front.components :as pcomp]
            [proglearn-front.apicalls :as apis]
            [cljs.core.async :refer [<!]]
            [proglearn-front.semcomponents :as s])
  (:import goog.History))

(enable-console-print!)

(println "This text is printed from src/proglearn-front/core.cljs. Go ahead and edit it and see reloading in action.
Bleh bleh bleh")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (rgt/atom {:text "Hello world!"}))

(defroute "/" []
          (rgt/render [pcomp/parent-comp]
                      (js/document.getElementById "app")))

(defroute "/challenge" []
          (go
            (let [response (<! (apis/testrequest))
                  r (:data response)]
              (swap! s/chstate merge {:challenge r})
              (rgt/render [pcomp/parent-comp @s/chstate]
                          (js/document.getElementById "app")))))

(let [h (History.)]
  (events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (doto h (.setEnabled true)))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)