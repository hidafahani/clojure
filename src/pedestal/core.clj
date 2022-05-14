(ns pedestal.core
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [hiccup2.core :as hic]
            [reagent.core :as r]))

(defn hello
  [req]
  {:html   [:html
            [:body
             [:p {:style {:font-color "blue"}}
              "Hello"]
             [:a {:href (route/url-for :hello2)}
              "Click here"]]]
   :status 200})

(defn hello2
  [req]
  {:html   [:html
            [:body
             [:p {:style {:font-color "green"}}
              "Hi!"]
             [:a {:href (route/url-for :hello)}
              "Click here"]]]
   :status 200})

(defn main
  [req]
  {:status 200
   :html   [:html
            [:body
             [:form {:method "post"}
              [:label "The answer is :"
               [:input {:type "text"
                        :placeholder "type here"}]]
              [:button {:type :submit} "Submit"]]]]})


(def html-response
  {:name  ::html-response
   :leave (fn [{:keys [response]
                :as   ctx}]
            (if (contains? response :html)
              (let [html-body (->> response
                                   :html
                                   (hic/html {:mode :html})
                                   (str "\n"))]
                (assoc ctx :response (-> response
                                         (assoc :body html-body)
                                         (assoc-in [:headers "Content-Type"] "text/html"))))
              ctx))})

(def routes
  #{["/" :get [html-response main] :route-name :main]
    ["/hello" :get [html-response hello] :route-name :hello]
    ["/hello2" :get [html-response hello2] :route-name :hello2]})

(def service-map
  {::http/routes routes
   ::http/type   :jetty
   ::http/port   8080})

(defn start []
  (http/start (http/create-server service-map)))

(defonce server (atom nil))

(defn start-dev []
  (reset! server
          (http/start (http/create-server
                        (assoc service-map
                          ::http/join? false)))))

(defn stop-dev []
  (http/stop @server))

(defn restart []
  (stop-dev)
  (start-dev))