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
            [proglearn-front.flow :as flow]
            [proglearn-front.state :as st :refer [app-state]])
  (:import goog.history.Html5History))

(enable-console-print!)

(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen
      EventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;(defn hook-browser-navigation! []
;  (let [history (doto (Html5History.)
;                  (events/listen
;                    EventType/NAVIGATE
;                    (fn [event]
;                      (secretary/dispatch! (.-token event))))
;                  (.setUseFragment false)
;                  (.setPathPrefix "")
;                  (.setEnabled true))]
;
;    (events/listen js/document "click"
;                   (fn [e]
;                     (. e preventDefault)
;                     (let [path (.getPath (.parse Uri (.-href (.-target e))))
;                           title (.-title (.-target e))]
;                       (when path
;                         (.history (setToken path title))))))))

(defn app-routes
  []
  ;(secretary/set-config! :prefix "#")
  (defroute "/" []
            (swap! app-state assoc :page :home))

  (defroute "/challenge" []
            (go
              (let [response (<! (apis/testrequest))
                    r (:data response)]
                (st/add-to-state [:lesson] r)
                (st/add-to-state [:progress] {:percent 20})
                ;(flow/reset-data)
                (flow/load-next-task)
                (swap! app-state assoc :page :challenge)))
            )
            (hook-browser-navigation!))

(defmulti current-page #(@app-state :page))

(defmethod current-page :challenge []
  [pc/parent-comp])

(defmethod current-page :home []
  [:div "placeholder"])

(defn ^:export main []
  (app-routes)
  (rgt/render [current-page]
              (js/document.getElementById "app")))

(main)
