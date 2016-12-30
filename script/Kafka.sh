#新建一个topic
kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic httpResponse
kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic httpRequest
kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic dnsResponse
kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic dnsRequest

#查询topic情况
kafka-topics.sh --list --zookeeper localhost:2182

#查询topic消息
kafka-console-consumer.sh --zookeeper localhost:2182 --topic httpRequest --from-beginning
