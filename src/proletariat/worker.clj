(ns proletariat.worker
  (:use [proletariat.mq]
        [proletariat.job]
        [proletariat.logging]))

(defn start-worker []
  (doseq [request-message (message-seq WORKER-QUEUE)]
    (handle-job-request request-message)))

(defn -main []
  (log "Starting worker...")
  (with-mq ["127.0.0.1" "guest" "guest"]
    (start-worker)))
