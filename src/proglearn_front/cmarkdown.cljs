(ns proglearn-front.cmarkdown
  (:require [reagent.core :as rgt]))

(def marktext "# proglearn-front\n\n`FIXME: Write a one-line description of your library/project.`\n\n## Overview\n\nFIXME: Write a paragraph about the library/project and highlight its goals.\n\n## Setup\n\nTo get an interactive development environment run:\n\n    lein figwheel\n\nand open your browser at [localhost:3449](http://localhost:3449/).\nThis will auto compile and send all changes to the browser without the\nneed to reload. After the compilation process is complete, you will\nget a Browser Connected REPL. An easy way to try it is:\n\n    (js/alert \"Am I connected?\")\n\nand you should see an alert in the browser window.\n\nTo clean all compiled files:\n\n    lein clean\n\nTo create a production build run:\n\n    lein do clean, cljsbuild once min\n\nAnd open your browser in `resources/public/index.html`. You will not\nget live reloading, nor a REPL. \n\n## License\n\nCopyright © 2014 FIXME\n\nDistributed under the Eclipse Public License either version 1.0 or (at your option) any later version.\n")


(defn- markdown-render [content]
  [:div {:dangerouslySetInnerHTML
         {:__html (-> content str js/marked)}}])

(defn- highlight-code [html-node]
  (let [nodes (.querySelectorAll html-node "pre code")]
    (loop [i (.-length nodes)]
      (when-not (neg? i)
        (when-let [item (.item nodes i)]
          (.highlightBlock js/hljs item))
        (recur (dec i))))))

(defn- markdown-did-mount [this]
  (let [node (rgt/dom-node this)]
    (highlight-code node)))

(defn- markdown-component [content]
  (rgt/create-class
    {:reagent-render      markdown-render
     :component-did-mount markdown-did-mount}))

(defn preview [content]
  (when (not-empty content)
    [markdown-component content]))

(defn react-preview
  "Make the component a react component"
  [content]
  (rgt/as-element (preview content)))