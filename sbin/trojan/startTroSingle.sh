spark-submit \
--class "com.rpc.Broker" \
target/scala-2.10/simple-project_2.10-1.0.jar &

sleep 3

spark-submit \
--class "com.TrojanTestBackend" \
target/scala-2.10/simple-project_2.10-1.0.jar &

sleep 10

spark-submit \
--class "com.TrojanTestFrontend" \
target/scala-2.10/simple-project_2.10-1.0.jar

