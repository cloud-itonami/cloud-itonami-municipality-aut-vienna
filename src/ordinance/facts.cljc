(ns ordinance.facts
  "Municipal-ordinance compliance catalog for Vienna -- the
  FORTY-FIRST municipality-level entry (see cloud-itonami-municipality-jpn-tokyo,
  -usa-washington-dc, -gbr-london, -can-toronto, -deu-berlin, -fra-paris,
  -nld-amsterdam, -esp-madrid, -kor-seoul, -ita-roma, -aus-sydney,
  -arg-buenos-aires, -fin-helsinki, -dnk-copenhagen, -nor-oslo,
  -bel-brussels, -chl-santiago, -col-bogota, -cri-san-jose,
  -bra-sao-paulo, -ury-montevideo, -zaf-cape-town, -ecu-quito,
  -swe-gothenburg, -pry-asuncion, -mex-guadalajara, -fra-lyon,
  -ind-new-delhi, -pol-warsaw, -ken-nairobi, -tha-bangkok, -are-abu-dhabi,
  -vnm-hanoi, -idn-jakarta, -phl-manila, -egy-cairo, -tur-ankara,
  -nga-abuja, -sau-riyadh, -mys-kuala-lumpur for the first forty) per
  ADR-2607141700 (cloud-itonami-compliance-fact-federation). Austria's
  first entry across any of the 3 axes.

  Vienna is Austria's stable capital, with no ongoing ambiguity.

  Austria's official federal legal-information system
  (ris.bka.gv.at) returned HTTP 503 Service Unavailable on both
  attempts, so this catalog's entries instead cite verfassungen.at (a
  constitutional/statutory text archive) and
  geschichtewiki.wien.gv.at (Wien Geschichte Wiki, an official City
  of Vienna cultural-heritage domain).

  Verfassung der Bundeshauptstadt Wien / Wiener Stadtverfassung
  (Constitution of the Federal Capital of Vienna) -- title and 10
  November 1920 enactment date (LGBl. für Wien Nr. 1) directly
  confirmed via verfassungen.at, which quotes the law's own citation
  line verbatim: 'Verfassung der Bundeshauptstadt Wien vom 10.
  November 1920, LGBl. für Wien Nr. 1'. Vienna is simultaneously a
  city AND one of Austria's nine federal states (Bundesländer), and
  this Stadtverfassung serves as both its municipal charter and its
  state constitution.

  First Bauordnung (Vienna building code) -- directly confirmed via
  geschichtewiki.wien.gv.at (an official City of Vienna domain),
  which states verbatim: 'Am 13. Dezember 1829 wurde erstmals eine
  Gesetzessammlung zur Regulierung des privaten Bauwesens unter dem
  Titel einer Bauordnung erlassen.' (On 13 December 1829, a
  collection of laws regulating private construction was enacted for
  the first time under the title of a building code.) The same page
  documents several later major revisions (1859, 1868, 1883, 1929),
  but this entry cites the original 1829 enactment.

  An ordinance not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url/date.")

(def catalog
  "municipality-slug -> vector of ordinance entries."
  {"vienna"
   [{:ordinance/id "vienna.wstv-1920-stadtverfassung"
     :ordinance/title "Verfassung der Bundeshauptstadt Wien (Wiener Stadtverfassung)"
     :ordinance/municipality "vienna"
     :ordinance/country "AUT"
     :ordinance/kind :local-act
     :ordinance/number "LGBl. für Wien Nr. 1/1920"
     :ordinance/url "https://www.verfassungen.at/wien/verf68.htm"
     :ordinance/url-provenance :verfassungen-at-legal-text-archive
     :ordinance/enacted-date "1920-11-10"
     :ordinance/retrieved-at "2026-07-17"
     :ordinance/topic #{:governance}}
    {:ordinance/id "vienna.bauordnung-1829-first-building-code"
     :ordinance/title "First Bauordnung (Vienna building code)"
     :ordinance/municipality "vienna"
     :ordinance/country "AUT"
     :ordinance/kind :local-act
     :ordinance/number "Bauordnung 1829"
     :ordinance/url "https://www.geschichtewiki.wien.gv.at/Bauordnung"
     :ordinance/url-provenance :official-wien-geschichte-wiki
     :ordinance/enacted-date "1829-12-13"
     :ordinance/retrieved-at "2026-07-17"
     :ordinance/topic #{:construction}}]})

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
      :note (str "cloud-itonami-municipality-aut-vienna Wave 0 (ADR-2607141700): "
                 (count (get catalog "vienna")) " Vienna entries seeded "
                 "with verfassungen.at/geschichtewiki.wien.gv.at citations. "
                 "Extend `ordinance.facts/catalog`, never fabricate an id/url.")})))

(defn by-topic [muni topic]
  (filterv #(contains? (:ordinance/topic %) topic) (spec-basis muni)))
