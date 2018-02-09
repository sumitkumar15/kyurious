(ns proglearn-front.semcomponents
  (:require [fulcrologic.semantic-ui.factories :as fs]
            [fulcrologic.semantic-ui.icons :as ic]
            [reagent.core :as rgt]
            [proglearn-front.editor :refer [editor]]
            [proglearn-front.cmarkdown :as mark]
            [proglearn-front.uicomp :as ui]))


(def runbutton (fs/ui-button #js {:content       "Run"
                                  :icon          ic/play-icon
                                  :labelPosition "middle"
                                  :color         "green"}))

(defn button
  ([props]
   (fs/ui-button (clj->js props))))

(defn somefunc
  []
  (js/alert "alerted"))

(defn create-menu-item
  [content props callback]
  (let [prop-m (clj->js (merge props {:content content
                                      :onClick callback}))]
    (fs/ui-menu-item prop-m)))

(defn create-menu
  []
  (fs/ui-menu (clj->js {:widths   4
                        :pointing true
                        :children [(create-menu-item "Problem" {} somefunc)
                                   (create-menu-item "Submissions" {} somefunc)
                                   (create-menu-item "Discussions" {} somefunc)
                                   (create-menu-item "Editorial" {} somefunc)]})))

(defn row
  ([^:Reactcomp arg]
   (fs/ui-grid-row #js {:children (if (list? arg) arg [arg])}))
  ([^:Reactcomp arg props]
   (fs/ui-grid-row (clj->js (merge
                              {:children (if (list? arg) arg [arg])}
                              props)))))

(defn col
  [^:Map props]
  (fs/ui-grid-column (clj->js props)))

(defn codemirror
  []
  (col {:width    10
        :children [(row editor) (fs/ui-divider) (row runbutton)]}))

(declare change-mango)
(defn create-list-item
  [qinfo props c]
  (fs/ui-list-item (clj->js (merge {:icon    ic/pencil-icon
                                    :content c
                                    :onClick change-mango}
                                   props))))


;(defn list-comp
;  []
;  (fs/ui-list (clj->js {:selection     true
;                        :verticalAlign "middle"
;                        :children      [(create-list-item nil {} "Orange") @at]})))
(defn left-col
  ([]
   (fs/ui-grid-column (clj->js {:width 10})))
  ([args]
   (fs/ui-grid-column (clj->js {:width    10
                                :children (if (list? args) args [args])}))))
(defn right-col
  ([]
   (fs/ui-grid-column (clj->js {:width 6})))
  ([args]
   (fs/ui-grid-column (clj->js {:width    6
                                :children (if (list? args) args [args])}))))

(defmulti challenge-comp (fn [x] (:type x)))

(defmethod challenge-comp "code"
  [params])

(defmethod challenge-comp "mcq"
  [{level :level title :title
    desc  :desc content :content
    id    :challengeId}]
  (let [titlerow (row (fs/ui-header #js {:as        "h2"
                                         :content   title
                                         :textAlign "center"}))
        headrow (row (list (col {:width 4 :children [level]})
                           (col {:width 4 :children [id]})))
        descrow (row (col {:children [desc]}))
        maincontent (ui/parse-mcq-exercise content)]
    (col {:children [(fs/ui-segment
                       (clj->js {:raised   true
                                 :children [titlerow
                                            descrow
                                            (fs/ui-divider)
                                            maincontent]}))
                     (fs/ui-grid
                       #js {:children (row
                                        (list
                                          (col {:width 13})
                                          (col {:width    3
                                                :children [(button {:content "Submit"
                                                                    :color   "green"
                                                                    :loading true
                                                                    :onClick (fn [x y] (println x))})]}))
                                        {:columns 2})})]})))

(defmethod challenge-comp "read"
  [params])

(defn grid
  [params]
  (fs/ui-grid #js {:container true
                   :centered  true
                   :padded    "vertically"
                   :divided   true
                   :children  [(left-col (list (challenge-comp params)))]}))

