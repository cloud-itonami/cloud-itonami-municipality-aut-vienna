(ns culture.facts
  "Regional-culture catalog for Vienna -- local dishes,
  protected products, beverages, festivals and heritage sites, piggybacked
  onto this municipality compliance repo per ADR-2607171400
  (cloud-itonami-municipality-culture-catalog, in com-junkawasaki/root),
  sibling namespace to `ordinance.facts` (ADR-2607141700).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "municipality-slug -> vector of culture entries."
  {"vienna"
   [{:culture/id "vienna.dish.wiener-schnitzel"
     :culture/name "Wiener schnitzel"
     :culture/municipality "vienna"
     :culture/country "AUT"
     :culture/kind :dish
     :culture/summary "Thin, breaded, pan-fried veal cutlet; one of the national dishes of Austria and a specialty of Viennese cuisine."
     :culture/url "https://en.wikipedia.org/wiki/Wiener_schnitzel"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vienna.dish.tafelspitz"
     :culture/name "Tafelspitz"
     :culture/municipality "vienna"
     :culture/country "AUT"
     :culture/kind :dish
     :culture/summary "Boiled veal or beef in broth served with minced apples and horseradish; a classic Viennese dish popular throughout Austria."
     :culture/url "https://en.wikipedia.org/wiki/Tafelspitz"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vienna.dish.sachertorte"
     :culture/name "Sachertorte"
     :culture/municipality "vienna"
     :culture/country "AUT"
     :culture/kind :dish
     :culture/summary "Chocolate sponge cake with apricot jam and chocolate glaze, invented by Austrian confectioner Franz Sacher; Vienna's Hotel Sacher and Demel remain its primary producers."
     :culture/url "https://en.wikipedia.org/wiki/Sachertorte"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vienna.dish.apfelstrudel"
     :culture/name "Apfelstrudel"
     :culture/municipality "vienna"
     :culture/country "AUT"
     :culture/kind :dish
     :culture/summary "Traditional Viennese apple strudel, a popular pastry in Austria; Vienna is its historic place of origin."
     :culture/url "https://en.wikipedia.org/wiki/Apfelstrudel"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vienna.beverage.wiener-gemischter-satz"
     :culture/name "Wiener Gemischter Satz"
     :culture/municipality "vienna"
     :culture/country "AUT"
     :culture/kind :beverage
     :culture/summary "Viennese field-blend wine; a protected designation of its own since 2011, carrying DAC (Districtus Austriae Controllatus) status from the 2013 vintage."
     :culture/url "https://de.wikipedia.org/wiki/Gemischter_Satz"
     :culture/url-provenance :wikipedia-de
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vienna.craft.snow-globe"
     :culture/name "Snow globe"
     :culture/name-local "Schneekugel"
     :culture/municipality "vienna"
     :culture/country "AUT"
     :culture/kind :craft
     :culture/summary "Transparent sphere enclosing a miniaturized snow scene; Austrian Erwin Perzy obtained the first patent for it and opened a shop in Vienna where production continues today."
     :culture/url "https://en.wikipedia.org/wiki/Snow_globe"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vienna.festival.donauinselfest"
     :culture/name "Donauinselfest"
     :culture/municipality "vienna"
     :culture/country "AUT"
     :culture/kind :festival
     :culture/summary "Free open-air music festival held annually on the Donauinsel in Vienna, the largest open-air music festival in the world."
     :culture/url "https://en.wikipedia.org/wiki/Donauinselfest"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vienna.heritage.schoenbrunn-palace"
     :culture/name "Schönbrunn Palace"
     :culture/name-local "Schloss Schönbrunn"
     :culture/municipality "vienna"
     :culture/country "AUT"
     :culture/kind :heritage
     :culture/summary "Baroque palace ensemble in Vienna, inscribed on the UNESCO World Heritage List in 1996."
     :culture/url "https://en.wikipedia.org/wiki/Sch%C3%B6nbrunn_Palace"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vienna.heritage.viennese-coffee-house"
     :culture/name "Viennese coffee house culture"
     :culture/name-local "Wiener Kaffeehauskultur"
     :culture/municipality "vienna"
     :culture/country "AUT"
     :culture/kind :heritage
     :culture/summary "Vienna's coffee-house culture, listed since October 2011 as an Intangible Cultural Heritage in UNESCO's Austrian inventory."
     :culture/url "https://en.wikipedia.org/wiki/Viennese_coffee_house"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [muni] (get catalog muni))

(defn coverage
  ([] (coverage (keys catalog)))
  ([munis]
   (let [have (filter catalog munis)
         missing (remove catalog munis)]
     {:requested (count munis)
      :covered (count have)
      :covered-municipalities (vec (sort have))
      :missing-municipalities (vec (sort missing))
      :note (str "cloud-itonami-municipality-aut-vienna culture catalog "
                 "(ADR-2607171400): " (count (get catalog "vienna"))
                 " Vienna entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [muni kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis muni)))
