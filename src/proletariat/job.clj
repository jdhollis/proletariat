(ns proletariat.job
  (:use [proletariat.mq]
        [proletariat.logging]
        [clj-stacktrace.repl :as strp]))

(def jobs (ref {}))
(def WORKER-QUEUE "jobs")

(defn request-envelope [job-name args]
  {:job-name job-name :job-args args})

(defn dispatch-job [job-name args]
  (let [job-request (request-envelope job-name args)]
    (log "Dispatching: %s to: %s" job-request WORKER-QUEUE)
    (send-message WORKER-QUEUE job-request)))

(defmacro job-runner [job-name job-args]
  `(fn ~job-args
     (dispatch-job ~job-name ~job-args)))

(defmacro defjob [service-name args & exprs]
  `(let [job-name# (keyword '~service-name)]
     (dosync
      (alter jobs assoc job-name# (fn ~args (do ~@exprs))))
     (def ~service-name (job-runner job-name# ~args))))

(defn handle-job-request [request-message]
  (try
    (let [job-request (read-string request-message)
          job-name (job-request :job-name)
          job-args (job-request :job-args)
          job-handler (@jobs job-name)]
      (if (not (nil? job-handler))
        (log "Processing: %s with args: %s" job-name job-args)
        (future
          (try
            (apply job-handler job-args)
            (catch Exception e
              (log "Error processing job:\n%s" (strp/pst-str e)))))))
    (catch Exception e
      (log "Error before job could be processed:\n%s" (strp/pst-str e)))))
