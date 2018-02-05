(ns proglearn-front.editor
  (:require [reagent.core :as r]
            [cljsjs.codemirror]
            ;[cljsjs.codemirror.addon.edit.closebrackets]
            ;[cljsjs.codemirror.addon.comment.comment]
            ;[cljsjs.codemirror.addon.comment.continuecomment]
            ;[cljsjs.codemirror.addon.dialog.dialog]
            ;[cljsjs.codemirror.addon.display.autorefresh]
            ;[cljsjs.codemirror.addon.display.fullscreen]
            ;[cljsjs.codemirror.addon.display.panel]
            ;[cljsjs.codemirror.addon.display.placeholder]
            ;[cljsjs.codemirror.addon.display.rulers]
            ;[cljsjs.codemirror.addon.edit.closebrackets]
            ;[cljsjs.codemirror.addon.edit.closetag]
            ;[cljsjs.codemirror.addon.edit.continuelist]
            ;[cljsjs.codemirror.addon.edit.matchbrackets]
            ;[cljsjs.codemirror.addon.edit.matchtags]
            ;[cljsjs.codemirror.addon.edit.trailingspace]
            ;[cljsjs.codemirror.addon.fold.brace-fold]
            ;[cljsjs.codemirror.addon.fold.comment-fold]
            ;[cljsjs.codemirror.addon.fold.foldcode]
            ;[cljsjs.codemirror.addon.fold.foldgutter]
            ;[cljsjs.codemirror.addon.fold.indent-fold]
            [cljsjs.codemirror.mode.clojure]
            ))

(def cm (r/atom (js/CodeMirror.
                      (.createElement js/document "div")
                      (clj->js
                        {:lineNumbers       true
                         :mode              "clojure"
                         :value             "//Code goes Here\n(+ 1 2 3 4)"
                         :theme             "default"}))))

(defn update-comp [this]
  (when @cm
    (when-let [node (r/dom-node this)]
      (.appendChild node (.getWrapperElement @cm)))))

(def ed (r/atom (r/create-class
                      {:reagent-render       (fn [] @cm [:div {:id "teditor"}])
                       :component-did-update update-comp
                       :component-did-mount  update-comp})))

(def editor (r/as-element [@ed]))
