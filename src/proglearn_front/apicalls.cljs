(ns proglearn-front.apicalls
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(defn testrequest
  []
  (go
    (let [{status :status
           body   :body} (<! (http/get "http://localhost:3000/api/challenge"
                                 {:with-credentials? false}))]
      (if (= 200 status)
        body))))

(defn check-ans
  [ans]
  (go
    (let [{status :status
           body   :body} (<! (http/post "http://localhost:3000/api/check"
                                       {:with-credentials? false
                                        :json-params ans}))]
      (if (= 200 status)
        body))))