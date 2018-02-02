(ns proglearn-front.components
  (:require [reagent.core :as rgt]
            [cljsjs.codemirror]))

(def question-text (rgt/atom "Some dummy question text?"))

;(def ta (rgt/atom (rgt/create-element "textarea")))
;
;(def atm (rgt/atom {}))
;
;;(def cm (js/CodeMirror.fromTextArea @ta {}))
(def cm (atom nil))

(reset! cm (js/CodeMirror.
             (.createElement js/document "div")
             (clj->js
               {:lineNumbers       20
                :viewportMargin    js/Infinity
                :matchBrackets     true
                :autofocus         true
                :autoCloseBrackets true
                :mode              "clojure"})))

(defn update-comp [this]
  (when @cm
    (when-let [node (or (js/document.getElementById "teditor")
                        (rgt/dom-node this))]
      (.appendChild node (.getWrapperElement @cm)))))

(def ed (rgt/atom (rgt/create-class
                    {:reagent-render       (fn [] @cm [:div {:id "teditor"}])
                     :component-did-update update-comp
                     :component-did-mount  update-comp})))

(defn question-comp
  []
  [:div {:class "ques-view"}
   [:div {:class "ques-text"}
    [:p @question-text]]])

(defn editor-comp
  []
  [:div {:class "editor-view"}
   [:div {:class "editor-options"}]
   [:div {:class "editor" :id "editormain"}
    [:div {:id "teditor"}]
    [@ed]]
   [:div {:class "editor-controls"}]])

(defn result-comp
  []
  [:div {:class "result-view"}])

(defn challenge-comp
  "Actual component composed of challenge-comp, editor, result-comp"
  []
  [:div {:class "challenge-view"}
   [question-comp]
   [editor-comp]
   [result-comp]])

(defn top-comp []
  [:div {:id "top"}])

(defn content-comp []
  [:div {:id "content"}
   [challenge-comp]])

(defn footer-comp []
  [:div {:id "footer"}])

(defn parent-comp
  []
  [:div {:id "parent"}
   [top-comp]
   [content-comp]
   [footer-comp]])
