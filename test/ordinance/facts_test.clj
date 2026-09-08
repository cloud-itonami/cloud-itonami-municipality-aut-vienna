(ns ordinance.facts-test
  (:require [kotoba.lang.text :as str]
            [clojure.test :refer [deftest is]]
            [ordinance.facts :as facts]))

(deftest vienna-has-spec-basis
  (let [sb (facts/spec-basis "vienna")]
    (is (= 2 (count sb)))
    (is (every? #(str/starts-with? (:ordinance/url %) "https://") sb))
    (is (every? :ordinance/number sb))))

(deftest unknown-municipality-has-no-spec-basis
  (is (nil? (facts/spec-basis "graz")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["vienna" "graz"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["graz"] (:missing-municipalities c)))))

(deftest by-topic-filters
  (is (= ["vienna.bauordnung-1829-first-building-code"]
         (mapv :ordinance/id (facts/by-topic "vienna" :construction))))
  (is (empty? (facts/by-topic "vienna" :labor)))
  (is (empty? (facts/by-topic "graz" :construction))))
