(ns specter-graph.core-test
  (:require [clojure.test :refer :all]
            [specter-graph.core :refer :all]

            [com.rpl.specter :as s :refer [select transform]]

            [loom.graph :as loom]
            [loom.attr :as attr]
            [loom.alg :as la]))

(def graph-with-attrs
  {:edges
   [{:strength 0.7, :src "dog", :dst "mammal"}
    {:strength 0.7, :src "cat", :dst "mammal"}
    {:strength 0.7, :src "fox", :dst "mammal"}
    {:strength 0.7, :src "elk", :dst "mammal"}
    {:strength 0.7, :src "ant", :dst "insect"}
    {:strength 0.7, :src "bee", :dst "insect"}
    {:strength 0.7, :src "carp", :dst "fish"}
    {:strength 0.7, :src "pike", :dst "fish"}
    {:strength 0.1, :src "elk", :dst "cat"}
    {:strength 0.1, :src "ant", :dst "carp"}
    {:strength 0.1, :src "bee", :dst "elk"}
    {:strength 0.1, :src "cat", :dst "dog"}
    {:strength 0.1, :src "ant", :dst "fox"}
    {:strength 0.1, :src "cat", :dst "pike"}],
   :nodes
   [{:id "mammal", :group 0, :label "Mammals", :level 1}
    {:id "dog", :group 0, :label "Dogs", :level 2}
    {:id "cat", :group 0, :label "Cats", :level 2}
    {:id "fox", :group 0, :label "Foxes", :level 2}
    {:id "elk", :group 0, :label "Elk", :level 2}
    {:id "insect", :group 1, :label "Insects", :level 1}
    {:id "ant", :group 1, :label "Ants", :level 2}
    {:id "bee", :group 1, :label "Bees", :level 2}
    {:id "fish", :group 2, :label "Fish", :level 1}
    {:id "carp", :group 2, :label "Carp", :level 2}
    {:id "pike", :group 2, :label "Pikes", :level 2}]})

