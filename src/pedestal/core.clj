(ns pedestal.core
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.route :as route]
            [hiccup2.core :as hic]))

(defn hello
  [req]
  (println req)
  {:html [:html
          [:body
           [:p {:style {:background-color "lightgreen"}}
            "Hello from HTML in green"]
           [:a {:href (route/url-for :hello-yellow)}
            "go to yellow"]]]
   :status 200})

(defn hello-yellow
  [req]
  (println req)
  {:html [:html
          [:body
           [:p {:style {:background-color "yellow"}}
            "Hello from HTML in yellow"]
           [:a {:href (route/url-for :hello)}
            "go to green"]]]
   :status 200})

(defn coba
  [req]
  {:status 200
   :html [:html
          [:body
           [:form
            [:label "Please enter your username:"
             [:input {:type "text"}]]
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
  #{["/" :get [html-response hello] :route-name :hello]
    ["/yellow" :get [html-response hello-yellow] :route-name :hello-yellow]
    ["/coba" :get [html-response coba] :route-name :coba]})

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