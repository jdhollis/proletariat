(ns proletariat.logging)

(defn log [msg & vals]
  (let [line (apply format msg vals)]
    (locking System/out (println line))))