(ns proglearn-front.components
  (:require [reagent.core :as rgt]
            [proglearn-front.editor :refer [editor]]
            [fulcrologic.semantic-ui.factories :as f]
            [fulcrologic.semantic-ui.icons :as i]
            [proglearn-front.semcomponents :as sc]))

(defn editor-comp
  []
  [:div {:class "editor-view"}
   [:div {:class "editor-options"}]
   [:div {:class "editor" :id "editormain"}
    [@editor]]
   [:div {:class "editor-controls"}]])
;
;(defn result-comp
;  []
;  [:div {:class "result-view"}])
;
;(defn challenge-comp
;  "Actual component composed of challenge-comp, editor, result-comp"
;  []
;  [:div {:class "challenge-view"}
;   [question-comp]
;   [editor-comp]
;   [result-comp]])

(defn top-comp []
  [:div {:id "top" :class "top-div"}])

;(defn content-comp []
;  [:div {:id "content" :class "content-div"}
;   [challenge-comp]])

(defn footer-comp []
  [:div {:id "footer" :class "footer"}])

(defn parent-comp
  []
  [:div {:id "parent" :class "parent-top-level"}
   [top-comp]
   [sc/grid]
   ;[content-comp]
   [footer-comp]])

