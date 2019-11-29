(ns describe
  (:gen-class)
  (:require [topology-grapher.describe :as td]
            [topology-grapher.render :as tr]
            [jackdaw-topology :as jt]
            [jackdaw.streams :as js]
            [interop-topology :as interop]))

(defn topology-from-stream-builder
  [stream-builder]
  ;; extract the underlying streams builder
  (.build (js/streams-builder* stream-builder)))

(def meta-data
  {:domain "Big Corp"
   :subdomain "departement"
   :application "sample"})

(defn topologies
  []
  [{:fn (fn [_] (topology-from-stream-builder (jt/t1 (js/streams-builder))))
    :config {"application.id" "my-application-id"}}

   {:fn (fn [_] (topology-from-stream-builder (jt/t2 (js/streams-builder))))
    :config {"application.id" "my-second-application-id"}}

   {:fn (fn [_] (interop/gen-topology))
    :config {"application.id" "interop-topology"}}])

(defn live-render
  []
  (let [topologies (td/gen-topologies (topologies) meta-data)]
    (tr/render-graph (vals topologies) {:fmt "png" :mode "detail" :cache false})))