apt-get update
apt-get install vim git htop -y
apt-get install python-pip -y
cd /usr/local
export HADOOP_HOME=/usr/local/hadoop
export SPARK_HOME=/usr/local/spark
export HBASE_HOME=/usr/local/hbase
export MVN_HOME=/usr/local/maven
export PATH=$MVN_HOME/bin:$HADOOP_HOME/bin:$SPARK_HOME/bin:$HBASE_HOME/bin:/usr/bin:/usr/local/bin:/bin:/usr/local/sbin:/usr/sbin:/sbin
export SBT_HOME=/usr/local/sbt16
export PATH=$PATH:$SBT_HOME
export NEO4J_HOME=/usr/local/neo4j
export PATH=$PATH:$NEO4J_HOME/bin
export NEO4J_SPARK=/usr/local/neo4j-spark
export PATH=$PATH:$NEO4J_SPARK/bin
export HIVE_HOME=/usr/local/hive
export PATH=$PATH:$HIVE_HOME/bin
export KAFKA_HOME=/usr/local/kafka
export PATH=$PATH:$KAFKA_HOME/bin
export DBNS_HOME=/usr/local/spark/mycode/DBNS
cat ~/.bashrc >> $DBNS_HOME/script/java.sh
cp -f $DBNS_HOME/script/java.sh ~/.bashrc
source ~/.bashrc
echo "127.0.0.1    "$HOSTNAME >> /etc/hosts

$HADOOP_HOME/sbin/start-dfs.sh
$HADOOP_HOME/sbin/start-yarn.sh
$HADOOP_HOME/sbin/mr-jobhistory-daemon.sh start historyserver
$SPARK_HOME/sbin/start-all.sh
$HBASE_HOME/bin/start-hbase.sh
$KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties &
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties &
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server-1.properties &
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server-2.properties &
$KAFKA_HOME/bin/kafka-topics.sh --list --zookeeper localhost:2182
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic httpResponse
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic httpRequest
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic dnsResponse
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic dnsRequest
$SPARK_HOME/bin/run-example SparkPi 2>&1 | grep "Pi is"
hive --service hiveserver2 &
hive --service metastore &
$SPARK_HOME/sbin/start-thriftserver.sh &
cd /usr/local/mysql-connector-python-1.0.11/
python setup.py install
cd /usr/local
