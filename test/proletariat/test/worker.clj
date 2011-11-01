(ns proletariat.test.worker
  (:use [proletariat.mq]
        [proletariat.job]
        [proletariat.worker]
        [clojure.test]))

(def job-storage (ref {}))

(defjob push-sum-to-job-results [x y]
  (dosync
   (alter job-storage assoc :results (+ x y))))

(deftest job-handling
  (with-mq ["127.0.0.1" "guest" "guest"]
    (push-sum-to-job-results 1 2)
    (handle-job-request (next-message-from WORKER-QUEUE))
    (is (+ 1 2) (@job-storage :result))))