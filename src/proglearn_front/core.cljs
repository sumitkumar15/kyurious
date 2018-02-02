(ns proglearn-front.core
  (:require [secretary.core :as secretary :refer-macros [defroute]]
            [reagent.core :as rgt]
            [goog.events :as events]
            [goog.dom :as dom]
            [goog.history.EventType :as EventType]
            [proglearn-front.components :as pcomp])
  (:import goog.History))

(enable-console-print!)

(println "This text is printed from src/proglearn-front/core.cljs. Go ahead and edit it and see reloading in action.
Bleh bleh bleh")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (rgt/atom {:text "Hello world!"}))

(defroute "/" []
          (rgt/render [pcomp/parent-comp]
                      (js/document.getElementById "app"))
          ;(js/CodeMirror.fromTextArea (.getElementById "teditor")
          ;                            (clj->js {:linenumbers 20}))
          ;(rgt/render "Hello" (js/document.getElementById "app"))
          )

(defroute "/c" []
          (swap! pcomp/question-text #(str "Hello")))

(let [h (History.)]
  (events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (doto h (.setEnabled true)))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
