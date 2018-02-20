(ns proglearn-front.util
  (:require [fulcrologic.semantic-ui.factories :as fs]))

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