(ns client.state
  "Atoms that are referenced in multiple modules.")

(def timeseries-data (atom []))


(println (deref timeseries-data))
