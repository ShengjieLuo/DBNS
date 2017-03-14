$KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper-cluster.properties &
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server-cluster-1.properties &
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server-cluster-2.properties &

#新建一个topic
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper spark-master:2182 --replication-factor 1 --partitions 1 --topic httpResponse
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper spark-master:2182 --replication-factor 1 --partitions 1 --topic httpRequest
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper spark-master:2182 --replication-factor 1 --partitions 1 --topic dnsResponse
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper spark-master:2182 --replication-factor 1 --partitions 1 --topic dnsRequest
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper spark-master:2182 --replication-factor 1 --partitions 1 --topic natlog
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper spark-master:2182 --replication-factor 1 --partitions 1 --topic syslog
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper spark-master:2182 --replication-factor 1 --partitions 1 --topic netflow

#查询topic情况
$KAFKA_HOME/bin/kafka-topics.sh --list --zookeeper spark-master:2182

#查询topic消息
$KAFKA_HOME/bin/kafka-console-consumer.sh --zookeeper spark-master:2182 --topic httpRequest --from-beginning
