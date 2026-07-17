(ns culture.facts-test
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [culture.facts :as facts]))

(deftest vienna-has-culture-basis
  (let [sb (facts/spec-basis "vienna")]
    (is (= 9 (count sb)))
    (is (= (count sb) (count (set (map :culture/id sb)))))
    (is (every? #(str/starts-with? (:culture/url %) "https://") sb))
    (is (every? #(= "vienna" (:culture/municipality %)) sb))
    (is (every? #(= "AUT" (:culture/country %)) sb))
    (is (every? #(seq (:culture/summary %)) sb))
    (is (every? #(string? (:culture/retrieved-at %)) sb))))

(deftest unknown-municipality-has-no-basis
  (is (nil? (facts/spec-basis "graz")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["vienna" "graz"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["graz"] (:missing-municipalities c)))))

(deftest by-kind-filters
  (is (= 4 (count (facts/by-kind "vienna" :dish))))
  (is (= ["vienna.beverage.wiener-gemischter-satz"]
         (mapv :culture/id (facts/by-kind "vienna" :beverage))))
  (is (empty? (facts/by-kind "vienna" :product)))
  (is (empty? (facts/by-kind "graz" :dish))))

(deftest tx-file-matches-catalog
  (let [tx (edn/read-string (slurp "data/culture-tx.edn"))
        flat (mapcat val (sort-by key facts/catalog))]
    (is (= (vec flat) (vec tx)))))
