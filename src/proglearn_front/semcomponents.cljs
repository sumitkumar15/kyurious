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

(defn challenge-comp
  []
  (let [{level :level title :title
         desc  :desc content :content
         id    :challengeId} (:lesson @app-state)
        titlerow (row (fs/ui-header (clj->js {:as        "h2"
                                              :content   title
                                              :textAlign "center"})))
        headrow (row (list (col {:width 4 :children [level]})
                           (col {:width 4 :children [id]})))
        descrow (row (col {:children [desc]}))
        maincontent (ui/render-ui @flow/current-task)
        tickicon (ui/tickmark @ui/att)]
    (col {:children [(fs/ui-segment
                       (clj->js {:basic    true
                                 :children [titlerow
                                            descrow
                                            (fs/ui-divider)
                                            maincontent
                                            tickicon]}))
                     (fs/ui-divider #js {:section true
                                         :hidden  true})]})))

(defn grid
  "The parameter only tells which function to render"
  []
  (fs/ui-grid #js {:container true
                   :centered  true
                   ;:padded    "vertically"
                   :divided   true
                   :children  [(left-col (list (challenge-comp)))]}))

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
                                          :children      [(cr fs/ui-header {:as      "h2"
                                                                            :content "Kyurious"})]})
                                    (col {:width    4
                                          :children (nav-menu)})
                                    (col {:width    4
                                          :children (profile-menu "Sumit")})
                                    (col {:width 4}))
                              {:color "teal"})]})))

(defn submit-click
  [event opts]
  (ui/indicate-incorrect))

(defn b-check-component
  "The Navigation bar"
  []
  (fs/ui-grid (clj->js
                {:centered true
                 :padded   "vertically"
                 :children [(row
                              (list
                                (col {:width    5
                                      :children (button {:content  "Skip"
                                                         :circular true
                                                         :color    "blue"
                                                         :size     "huge"
                                                         :basic    true})})
                                (col {:width    5
                                      :children (button {:content  "Check"
                                                         :circular true
                                                         :color    "teal"
                                                         :size     "huge"
                                                         :floated  "right"}
                                                        submit-click)}))
                              {:color "teal"})]})))
