(ns proglearn-front.core
  (:require [secretary.core :as secretary :refer-macros [defroute]]
            [reagent.core :as rgt]
            [goog.events :as events]
            [goog.dom :as dom]
            [goog.history.EventType :as EventType])
  (:import goog.History))

(enable-console-print!)

(println "This text is printed from src/proglearn-front/core.cljs. Go ahead and edit it and see reloading in action.
Bleh bleh bleh")

;; define your app data so that it doesn't get over-written on reload

(defn some-component []
  [:div
   [:h3 "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red"]
    " text."]])

(defonce app-state (atom {:text "Hello world!"}))

(defn simple-component []
  [:div
   [:p "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])

(defn render-simple []
  (rgt/render [simple-component]
            (.-body js/document)))

(defroute "/compp" []
          (println "on route comp")
          (render-simple)
          ;(rgt/render "Hello" (js/document.getElementById "app"))
          )

(let [h (History.)]
  (events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (doto h (.setEnabled true)))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
