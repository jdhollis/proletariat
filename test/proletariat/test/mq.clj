(ns proletariat.test.mq
  (:use [proletariat.mq]
        [clojure.test]))

(deftest basic-send-and-receive
  (let [message "Hello, world"
        queue "proletariat.test"]
    (with-mq ["127.0.0.1" "guest" "guest"]
      (send-message queue message)
      (is message (next-message-from queue)))))

(deftest receive-via-seq
  (let [message "Hello, world"
        queue "proletariate.test"]
    (with-mq ["127.0.0.1" "guest" "guest"]
      (dotimes [n 3]
        (send-message queue message))
      (let [messages (take 3 (message-seq queue))]
        (doseq [received messages]
          (is message received))))))
