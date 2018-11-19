(ns specter-graph.core
  (:require [com.rpl.specter :as s :refer [select transform]]
            [com.rpl.specter.navs :as sn]
            [com.rpl.specter.util-macros :as su]

            [loom.graph :as loom]
            [loom.attr :as attr]
            [loom.alg :as la]))


(defn add-attrs-to-node-or-edge [g [node-or-edge-id attr-map]]
  "adds attributes to a single node or edge in a graph"
  (reduce (fn [inner-g [k v]]
            (attr/add-attr inner-g node-or-edge-id k v))
          g attr-map))

(defn add-attrs-to-nodes-or-edges [g node-or-edge-map]
  "adds attributes from multiple nodes or edges in a graph"
  (reduce
    add-attrs-to-node-or-edge
    g node-or-edge-map))


(s/defnav
  ^{:doc "performs topological sort returning a graph and a node-ref"}
  TOPSORT []
  (select* [this structure next-fn]
    (su/doseqres s/NONE [node-ref (la/topsort structure)]
       (next-fn [structure node-ref])))
  (transform* [this structure next-fn]
    (reduce (fn [graph node-id]
              (-> [graph node-id] next-fn (nth 0)))
            structure
            (la/topsort structure))))

(defn get-node [graph node-ref]
  (when (loom/has-node? graph node-ref)
    (if (attr/attrs graph node-ref)
      (attr/attrs graph node-ref)
      ;; It appears that for loom the node-ref is the node if there aren't any attributes.
      node-ref)))

(s/defnav
  ^{:doc "node-ref to node"}
  NODE []
  (select* [this structure next-fn]
    (let [[graph node-ref] structure]
      (next-fn (get-node graph node-ref))))
  (transform* [this structure next-fn]
    (let [[graph node-ref] structure]
      [(add-attrs-to-node-or-edge graph [node-ref (next-fn (get-node graph node-ref))])
       node-ref])))

(defn make-graph [graph-type {:keys [nodes edges] :as graph}]
  (let [node-map (into {} (map (fn [node] [(:id node) node])) nodes)
        edge-map (into {} (map (fn [edge] [[(:src edge) (:dst edge)] edge])) edges)]

    (-> (apply graph-type (keys edge-map))
        (add-attrs-to-nodes-or-edges node-map)
        (add-attrs-to-nodes-or-edges edge-map))))

(def make-weighted-digraph #(make-graph loom/weighted-digraph %))

