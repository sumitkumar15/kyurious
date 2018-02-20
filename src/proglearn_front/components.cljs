(ns proglearn-front.components
  (:require [reagent.core :as rgt]
            [proglearn-front.editor :refer [editor]]
            [fulcrologic.semantic-ui.factories :as f]
            [fulcrologic.semantic-ui.icons :as i]
            [proglearn-front.semcomponents :as sc]
            [proglearn-front.uicomp :as ui]
            [proglearn-front.state :refer [app-state]]))

(defn editor-comp
  []
  [:div {:class "editor-view"}
   [:div {:class "editor-options"}]
   [:div {:class "editor" :id "editormain"}
    [@editor]]
   [:div {:class "editor-controls"}]])

(defn top-comp []
  [:div {:id "top" :class "top-div"}])

(defn footer-comp []
  [:div {:id "footer" :class "footer"}])

(defn parent-comp
  []
  [:div {:id "parent" :class "parent-top-level"}
   [sc/nav-component]])

(defn playground
  "main area of app where user answers the questions"
  [^:Map data]
  [:div {:id "challenge"}
   [sc/mainapp data]])
