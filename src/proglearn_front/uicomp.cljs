(ns proglearn-front.uicomp
  (:require [reagent.core :as rgt]
            [fulcrologic.semantic-ui.factories :as fs]
            [fulcrologic.semantic-ui.icons :as ic]))

;; variable that holds the mcq quiz state
(def init-state {:completed false
                 :checkdisabled false})
(def btn-state {:size "huge"})

(def sol-state (rgt/atom init-state))
(def att (rgt/atom btn-state))

(declare enable-chk-btn)
(defn label
  [content]
  (fs/ui-header (clj->js {:as "h3"
                          :content content
                          :size "large"})))

(defn handle-radio-change
  [a]
  (swap! sol-state merge {:sol (.-value (.-target a))})
  (enable-chk-btn))

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
        (clj->js (merge {:name name
                         :disabled (:completed @sol-state)}
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

(defn tick-class
  [mark]
  (rgt/create-class
    {:reagent-render (fn [x]
                       (let [props (cond
                                     (false? x) {:name  ic/remove-circle-outline-icon
                                                 :color "red"
                                                 :size  "huge"}
                                     (true? x) {:name  ic/check-circle-outline-icon
                                                :color "green"
                                                :size  "huge"}
                                     :default {:size "huge"})]
                         (fs/ui-icon
                           (clj->js props))))}))

(defn tickmark
  [mark]
  (rgt/as-element
    [tick-class mark]))

;(defn set-completed
;  [a]
;  (if a
;    (swap! sol-state merge {:completed true})
;    (swap! sol-state merge {:completed false})))

(defn enable-chk-btn
  []
  (swap! sol-state merge {:checkdisabled false}))

(defn progress-bar-comp
  [percent]
  (fs/ui-progress (clj->js {:percent percent
                            :size    "tiny"
                            :color   "blue"})))