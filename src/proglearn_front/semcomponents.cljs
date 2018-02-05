(ns proglearn-front.semcomponents
  (:require [fulcrologic.semantic-ui.factories :as fs]
            [fulcrologic.semantic-ui.icons :as ic]
            [reagent.core :as rgt]
            [proglearn-front.editor :refer [editor]]))

(def question-info (rgt/atom {:title "Apple & Oranges"
                              :qtext "Sam's house has an apple tree and an orange tree
                      that yield an abundance of fruit. In the diagram
                       below, the red region denotes his house, where
                       is the start point, and is the endpoint. The apple
                       tree is to the left of his house, and the orange tree
                       is to its right. You can assume the trees are located on a
                       single point, where the apple tree is at point , and the
                       orange tree is at point ."}))

(def runbutton (fs/ui-button #js {:content       "Run"
                                  :icon          ic/play-icon
                                  :labelPosition "middle"
                                  :color         "green"}))

(defn parse-q-segment
  [arg]
  (fs/ui-segment #js {:raised  true
                      :content arg
                      :children [(fs/ui-header
                                   #js {:as "h2"
                                        :content (:title arg)})
                                 (rgt/as-element [:p (:qtext arg)])]}))

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

(defn create-row
  [^:Reactcomp arg]
  (fs/ui-grid-row #js {:children (if (list? arg) arg [arg])}))

(defn create-col
  [^:Map props]
  (fs/ui-grid-column (clj->js props)))

(defn codemirror
  []
  (create-col {:width    10
               :children [(create-row editor) (fs/ui-divider) (create-row runbutton)]}))

(def midrow (create-row (list (codemirror)
                              (create-col {:width 6}))))

(declare change-mango)
(defn create-list-item
  [qinfo props c]
  (fs/ui-list-item (clj->js (merge {:icon    ic/pencil-icon
                                    :content c
                                    :onClick change-mango}
                                   props))))

(def at (rgt/atom (create-list-item nil {:onClick change-mango} "Banana")))


(defn change-mango
  []
  (swap! at (fn [] (create-list-item {} nil "Mango"))))

(defn list-comp
  []
  (fs/ui-list (clj->js {:selection     true
                        :verticalAlign "middle"
                        :children      [(create-list-item nil {} "Orange") @at]})))
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

(defn grid
  []
  (fs/ui-grid #js {:container true
                   :centered  true
                   :padded    "vertically"
                   :divided   true
                   :children  [(left-col (list (create-menu)
                                               (create-row
                                                 (parse-q-segment @question-info))
                                               (fs/ui-divider)
                                               (codemirror)))
                               (right-col (list (list-comp)))]}))
