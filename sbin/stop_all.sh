source env.rc
$DBNS_HOME/sbin/stop_data.sh
$HADOOP_HOME/sbin/stop-all.sh
$HADOOP_HOME/sbin/mr-jobhistory-daemon.sh stop historyserver
$SPARK_HOME/sbin/stop-all.sh
$KAFKA_HOME/bin/kafka-server-stop.sh
$KAFKA_HOME/bin/zookeeper-server-stop.sh
sudo kill -9 `ps -aux | grep HiveServer2 | awk '{print $2}'`
sudo kill -9 `ps -aux | grep HiveMetaStore | awk '{print $2}'`
