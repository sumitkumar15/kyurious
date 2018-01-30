(ns proglearn-front.components)

(defn nav-component
  [])

(defn question-comp
  [qtext]
  [:div {:class "ques-view"}
   [:p qtext]])

(defn editor-comp
  [editor]
  [:div {:class "editor-view"}
   [:div {:class "editor-options"}]
   [:div {:class "editor"}]
   [:div {:class "editor-controls"}]])

(defn result-comp
  []
  [:div {:class "result-view"}])

(defn challenge-comp
  "Actual component composed of challenge-comp, editor, result-comp"
  [question editor result]
  [:div {:class "challenge-view"}
   [question-comp question]
   [editor-comp editor]
   [result-comp]])
