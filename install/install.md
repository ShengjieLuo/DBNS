#DBNS系统安装与运行

## Step1. 配置ssh无密码登录(手动测试)
```
cd ~/.ssh/
ssh-keygen -t rsa
cat ./id_rsa.pub >> ./authorized_keys
ssh localhost
```
检查是否能无密码登录

## Step2. 安装mysql,配置数据库
sudo apt-get install mysql-server
user:123456
Password: 123456
mysql -u root -p
进入mysql交互后，将下列指令直接复制黏贴进入mysql

```
set global sql_mode='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
create database web;
use web;
create table onHRSips (id varchar(20),IPSource varchar(20),count int);
create table onHRSipd (id varchar(20),IPDest varchar(20),count int);
create table onHRSps (id varchar(20),PortSource varchar(20),count int);
create table onHRSpd (id varchar(20),PortDest varchar(20),count int);
create table onHRSrc (id varchar(20),returnCode varchar(20),count int);
create table onHRQips (id varchar(20), IPSource varchar(20),count int);
create table onHRQipd (id varchar(20),IPDest varchar(20),count int);
create table onHRQps (id varchar(20),PortSource varchar(20),count int);
create table onHRQpd (id varchar(20),PortDest varchar(20),count int);
create table onDRSips(id varchar(20),IPSource varchar(20),count int);
create table onDRSipd(id varchar(20),IPDest varchar(20),count int);
create table onDRSname(id varchar(20),name varchar(100),count int);
create table onDRStype(id varchar(20),type varchar(20),count int);
create table onDRSurl(id varchar(20),url varchar(100),count int);
create table onDRQips(id varchar(20),IPSource varchar(20),count int);
create table onDRQipd(id varchar(20),IPDest varchar(20),count int);
create table onDRQname(id varchar(20),name varchar(100),count int);
create table onDRQtype(id varchar(20),type varchar(20),count int);
create table ofHRSips (id varchar(20),IPSource varchar(20),count int);
create table ofHRSipd (id varchar(20),IPDest varchar(20),count int);
create table ofHRSps (id varchar(20),PortSource varchar(20),count int);
create table ofHRSpd (id varchar(20),PortDest varchar(20),count int);
create table ofHRSrc (id varchar(20),returnCode varchar(20),count int);
create table ofHRQips (id varchar(20), IPSource varchar(20),count int);
create table ofHRQipd (id varchar(20),IPDest varchar(20),count int);
create table ofHRQps (id varchar(20),PortSource varchar(20),count int);
create table ofHRQpd (id varchar(20),PortDest varchar(20),count int);
create table ofDRSips(id varchar(20),IPSource varchar(20),count int);
create table ofDRSipd(id varchar(20),IPDest varchar(20),count int);
create table ofDRSname(id varchar(20),name varchar(100),count int);
create table ofDRStype(id varchar(20),type varchar(20),count int);
create table ofDRSurl(id varchar(20),url varchar(100),count int);
create table ofDRQips(id varchar(20),IPSource varchar(20),count int);
create table ofDRQipd(id varchar(20),IPDest varchar(20),count int);
create table ofDRQname(id varchar(20),name varchar(100),count int);
create table ofDRQtype(id varchar(20),type varchar(20),count int);
create database stat;
use stat;
create table HRSips (id varchar(20),IPSource varchar(20),count int);
create table HRSipd (id varchar(20),IPDest varchar(20),count int);
create table HRSps (id varchar(20),PortSource varchar(20),count int);
create table HRSpd (id varchar(20),PortDest varchar(20),count int);
create table HRSrc (id varchar(20),returnCode varchar(20),count int);
create table HRQips (id varchar(20), IPSource varchar(20),count int);
create table HRQipd (id varchar(20),IPDest varchar(20),count int);
create table HRQps (id varchar(20),PortSource varchar(20),count int);
create table HRQpd (id varchar(20),PortDest varchar(20),count int);
create table DRSips(id varchar(20),IPSource varchar(20),count int);
create table DRSipd(id varchar(20),IPDest varchar(20),count int);
create table DRSname(id varchar(20),name varchar(100),count int);
create table DRStype(id varchar(20),type varchar(20),count int);
create table DRSurl(id varchar(20),url varchar(100),count int);
create table DRQips(id varchar(20),IPSource varchar(20),count int);
create table DRQipd(id varchar(20),IPDest varchar(20),count int);
create table DRQname(id varchar(20),name varchar(100),count int);
create table DRQtype(id varchar(20),type varchar(20),count int);
create database hive;
grant all on *.* to hive@localhost identified by 'hive';
flush privileges;
```

## Step3. 执行安装脚本
将bin文件包解压到/usr/local后执行下列脚本，您可以选择手动输入以下指令，或者直接使用我们提供的安装脚本

source $DBNS_HOME/script/install.sh
```
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
```

## Step4 通过beeline配置hive数据库

beeline

!connect jdbc:hive2://127.0.0.1:10000

Enter username for jdbc:hive2://127.0.0.1:10000: root

Enter password for jdbc:hive2://127.0.0.1:10000: 123456

进入beeline交互之后直接复制黏贴下列指令
```
create database if not exists HRS;
create database if not exists HRQ;
create database if not exists DRS;
create database if not exists DRQ;
create table if not exists HRS.original(time string, TTL string, ips string, ps string, ipd string, pd string, rc string);
create table if not exists HRQ.original(time string, TTL string, ips string, ps string, ipd string, pd string, type string);
create table if not exists DRQ.original(time string, ips string, ipd string, name string, type string, class string);
create table if not exists DRS.original(time string, ips string, ipd string, name string, type string, class string, TTL string, url string);
```

## Step5 通过下面命令测试是否能正常运行
```
$DBNS_HOME/sbin/start_dbns_standard.sh
```

