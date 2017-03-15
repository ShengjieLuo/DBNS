/usr/local/spark/bin/spark-submit \
--executor-memory 10G  \
--driver-memory 20G \
--master spark://spark-master:7077 \
--class "LogUDPProducer" \
--total-executor-cores 10 \
/usr/local/DBNS/target/scala-2.10/simple-project_2.10-1.0.jar \
spark-master:9092 0 1 test 9999
