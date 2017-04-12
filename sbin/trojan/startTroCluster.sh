spark-submit \
--class "com.TrojanTest" \
--master spark://172.16.0.104:7077 \
--total-executor-cores 6 \
--executor-cores 3 \
target/scala-2.10/simple-project_2.10-1.0.jar
