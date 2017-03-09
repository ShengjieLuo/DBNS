nohup $KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper-cluster.properties >>$DBNS_HOME/log/zookeeper.log 2>&1 &
sleep 3s
nohup $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server-cluster-1.properties >>$DBNS_HOME/log/kafka01.log 2>&1 &
sleep 3s
nohup $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server-cluster-2.properties  >>$DBNS_HOME/log/kafka02.log 2>&1 &
sleep 3s
$KAFKA_HOME/bin/kafka-topics.sh --list --zookeeper 172.16.0.104:2182
