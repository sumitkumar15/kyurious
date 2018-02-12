(ns proglearn-front.uicomp
  (:require [reagent.core :as rgt]
            [fulcrologic.semantic-ui.factories :as fs]
            [fulcrologic.semantic-ui.icons :as ic]))

;; variable that holds the mcq quiz state
(def mcq-state (atom {}))

(defn label
  [content]
  (fs/ui-label (clj->js {:content content
                         :size "big"})))

(defn handle-radio-change
  [a]
  (swap! mcq-state (fn [] (merge @mcq-state
                                 {(.-name (.-target a))
                                  (.-value (.-target a))}))))

(defn form-field-opts
  [^:Map data]
  {:control  "input"
   :label    (:label data)
   :value    (:value data)
   :type     "radio"
   :onClick handle-radio-change})

(defn prepare-opts
  [^:Map data]
  (let [name (:id data) options (:options data)]
    (for [opt options]
      (fs/ui-form-field
        (clj->js (merge {:name name}
                        (form-field-opts opt)))))))

(defn generate-radio
  [^:Map data]
  (fs/ui-form-group
    (clj->js {:children [(label (:question data))
                         (fs/ui-divider #js {:hidden true})
                         (prepare-opts data)]})))

(defn create-mcq-class
  [^:Map data]
  (rgt/create-class
    {:reagent-render generate-radio}))

(defn mcq
  [content]
  (when (not-empty content)
    (rgt/as-element [create-mcq-class content])))

(defn parse-mcq-exercise
  [content]
  (-> (map (fn [x] [x (fs/ui-divider #js {:hidden true})]) (map mcq content))
      flatten))

(defmulti render-ui
          "Takes a task map & renders the ui component based on task type"
          (fn [x] (:type x)))

(defmethod render-ui "mcq"
  [content]
  (mcq content))

(defmethod render-ui "code"
  [content])

(defmethod render-ui "read"
  [content])
