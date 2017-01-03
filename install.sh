echo "*************************************"
echo "**  你必须在root权限下安装程序     **"
echo "*************************************"

apt-get install vim -y
apt-get install git -y
cd ~/.ssh/
ssh-keygen -t rsa
cat ./id_rsa.pub >> ./authorized_keys
apt-get install -y openjdk-7-jre openjdk-7-jdk
echo "export JAVE_HOME=/usr/lib/jvm/java-7-openjdk-amd64" >> ~/.bashrc
source ~/.bashrc

tar zxvf DBNSv1.tar.gz -C /usr/local
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

$HADOOP_HOME/sbin/stop-all.sh
$SPARK_HOME/sbin/stop-all.sh
$HADOOP_HOME/sbin/start-dfs.sh
$HADOOP_HOME/sbin/start-yarn.sh
$HADOOP_HOME/sbin/mr-jobhistory-daemon.sh start historyserver
$SPARK_HOME/sbin/start-all.sh
$KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties &
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties &
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server-1.properties &
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server-2.properties &
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic wordsender
$KAFKA_HOME/bin/kafka-topics.sh --list --zookeeper localhost:2182
$HBASE_HOME/bin/start-hbase.sh

kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic httpResponse
kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic httpRequest
kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic dnsResponse
kafka-topics.sh --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic dnsRequest
