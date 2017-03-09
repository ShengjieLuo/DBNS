/usr/local/spark/bin/spark-submit \
--executor-memory 1.5G  \
--master spark://spark-master:7077 \
--class "LogProducer_1" \
--total-executor-cores 10 \
/usr/local/DBNS/target/scala-2.10/simple-project_2.10-1.0.jar \
172.16.0.104:9092 0 \
/usr/local/sample/http_response.txt\
/usr/local/sample/http_request.txt\
/usr/local/sample/dns_response.txt\
/usr/local/sample/dns_request.txt &

/usr/local/spark/bin/spark-submit \
--executor-memory 1.5G  \
--master spark://spark-master:7077 \
--class "LogProducer_2" \
--total-executor-cores 10 \
/usr/local/DBNS/target/scala-2.10/simple-project_2.10-1.0.jar \
172.16.0.104:9093 0 \
/usr/local/sample/netflow.txt \
/usr/local/sample/natlog.txt \
/usr/local/sample/syslog.txt &
