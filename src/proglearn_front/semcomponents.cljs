(ns proglearn-front.semcomponents
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [fulcrologic.semantic-ui.factories :as fs]
            [fulcrologic.semantic-ui.icons :as ic]
            [reagent.core :as rgt]
            [proglearn-front.editor :refer [editor]]
            [proglearn-front.cmarkdown :as mark]
            [proglearn-front.uicomp :as ui]
            [proglearn-front.apicalls :as apis]
            [cljs.core.async :refer [<!]]
            [proglearn-front.flow :as flow]
            [proglearn-front.state :as st :refer [app-state]]))

(defn button
  ([props]
   (fs/ui-button (clj->js props)))
  ([props callback]
   (fs/ui-button (clj->js (merge {:onClick callback} props)))))

(defn menu-item
  [content props callback]
  (let [prop-m (clj->js (merge props {:content content
                                      :onClick callback}))]
    (fs/ui-menu-item prop-m)))

(defn create-menu
  "(create-menu-item \"Problem\" {} somefunc)"
  [props & items]
  (fs/ui-menu (clj->js (merge {:widths   (count items)
                               :children items}
                              props))))
(defn cr
  "Sugarcoat  for (fs/xx (clj->js {}))"
  [f opts]
  (f (clj->js opts)))

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

;(defn codemirror
;  []
;  (col {:width    10
;        :children [(row editor) (fs/ui-divider) (row runbutton)]}))

(defn create-list-item
  [qinfo props c]
  (fs/ui-list-item (clj->js (merge {:icon    ic/pencil-icon
                                    :content c}
                                   props))))
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

(defmethod challenge-comp "mcq"
  [data]
  (let [maincontent (ui/render-ui data)]
    (col {:children [(fs/ui-segment
                       (clj->js {:basic    true
                                 :children [maincontent]}))
                     (fs/ui-divider #js {:section true
                                         :hidden  true})]})))

(defmethod challenge-comp "code"
  [])

(defn challenge-header
  [data]
  (fs/ui-header (clj->js {:as        "h2"
                          :content   data
                          :textAlign "center"})))

(defn continue-click
  [event opts]
  )

(defn check-click
  [event opts]
  )

(defn skip-click
  [event opts])

(def btns {:check    {:content  "Check"
                      :circular true
                      :color    "teal"
                      :size     "huge"
                      :floated  "right"
                      ;:disabled
                      :onClick check-click}
           :continue {:content  "Next"
                      :circular true
                      :color    "teal"
                      :size     "huge"
                      :floated  "right"
                      :onClick continue-click}
           :skip {:content  "Skip"
                  :circular true
                  :color    "blue"
                  :size     "huge"
                  :onClick skip-click}})

(defn check-comp
  [^:Map data]
  (let [{s :state m :marked} data]
    (row
      (list
        (col {:width    2
              :children (button (:skip btns))})
        (col {:width    3
              :children (ui/tickmark s)})
        (col {:width    3
              :children (button (:check btns)
                                check-click)})
        (col {:width    2
              :children (button (:continue btns)
                                continue-click)}))
      {:verticalAlign "middle"})))

(defn mainapp
  "Pass the complete question data"
  [^:Map data]
  (let [{head :heading chID :chID
         test :test progress :progress
         current :current} data]
    (fs/ui-grid #js {:container true
                     :centered  true
                     :children  [(fs/ui-divider #js {:section true
                                                     :hidden  true})
                                 (row (challenge-header head))
                                 (fs/ui-divider)
                                 (row (col {:children (ui/progress-bar-comp (:percent progress))}))
                                 (fs/ui-divider)
                                 (row (challenge-comp (:puzzle current)))
                                 (fs/ui-divider #js {:section true
                                                     :hidden  true})
                                 (check-comp (:check current))]})))

(defn nav-menu
  []
  (let [mopts {:secondary true} props {}]
    (create-menu
      mopts
      (button {:content  "Home"
               :circular true
               :color    "teal"})
      (button {:content  "Discussion"
               :circular true
               :color    "teal"}))))

(defn profile-menu
  [name]
  (let [header (cr fs/ui-dropdown-header
                   {:content "Profile"})
        dropitems [{:content "Settings"}
                   {:content "Help"}
                   {:content "Signout"}]
        dropf (partial cr fs/ui-dropdown-item)
        dropmenu (cr fs/ui-dropdown-menu
                     {:children [header
                                 (map dropf dropitems)]})]
    (cr fs/ui-dropdown {:text      name
                        :pointing  true
                        :className "link item"
                        :children  dropmenu})))

(defn nav-component
  "The Navigation bar"
  []
  (fs/ui-grid (clj->js
                {:centered true
                 :padded   "vertically"
                 :children [(row
                              (list (col {:width         4
                                          :textAlign     "center"
                                          :verticalAlign "middle"
                                          :children      [(cr fs/ui-header
                                                              {:as      "h2"
                                                               :content "Kyurious"})]})
                                    (col {:width    4
                                          :children (nav-menu)})
                                    (col {:width    4
                                          :verticalAlign "middle"
                                          :children (profile-menu "Sumit")})
                                    (col {:width 4}))
                              {:color "teal"})]})))

