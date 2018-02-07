(ns proglearn-front.uicomp
  (:require [reagent.core :as rgt]
            [fulcrologic.semantic-ui.factories :as fs]
            [fulcrologic.semantic-ui.icons :as ic]))

(def testdata {:question "How many legs on a snake How many legs on a snake How many legs on a snakeHow many legs on a snakeHow many legs on a snakeHow many legs on a snake?"
               :id "blabla"
               :options [{:label "1" :value "A"}
                         {:label "2" :value "B"}
                         {:label "3" :value "C"}
                         {:label "4" :value "D"}]})
(defn label
  [content]
  (fs/ui-label (clj->js {:content content
                         :size "big"})))

(defn handle-radio-change
  [this])

(defn set-form-field
  [^:Map data]
  (fs/ui-form-field (clj->js {:control "input"
                              :label (:label data)
                              :value (:value data)
                              :type "radio"
                              :onChange handle-radio-change})))
(defn form-field
  [])

(defn generate-radio
  [^:Map data]
  (fs/ui-form-group
    (clj->js {:children [(label (:question data))
                         (fs/ui-divider #js {:hidden true})
                         (map set-form-field (:options data))]})))

(defn create-radio-group
  [^:Map data]
  (rgt/create-class
    {:reagent-render generate-radio}))

(defn mcq
  [content]
  (when (not-empty content)
    (rgt/as-element [create-radio-group content])))
