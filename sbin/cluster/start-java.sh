echo  -e "\033[47,31m******************************************* \033[0m"
echo  -e "\033[47,31m**         Welcome to use DBNS!          ** \033[0m"
echo  -e "\033[47,31m** This script is used to stop DBNS      ** \033[0m"
echo  -e "\033[47,31m** 1. Start running java Process         ** \033[0m"
echo  -e "\033[47,31m** Shanghai Jiao Tong University         ** \033[0m"
echo  -e "\033[47,31m** Network Computing Lab                 ** \033[0m"
echo  -e "\033[47,31m** https://github.com/ShengjieLuo/DBNS   ** \033[0m"
echo  -e "\033[47,31m******************************************* \033[0m"

echo  -e "\033[31m******************************************* \033[0m"
echo  -e "\033[31m**         Stop running JAVA Process     ** \033[0m"
echo  -e "\033[31m******************************************* \033[0m"

export $DBNS_HOME=/usr/local/DBNS
source $DBNS_HOME/env.rc

echo -e "\033[31m Start Hadoop-related Process: \033[0m"
$DBNS_HOME/sbin/cluster/start-hadoop.sh
sleep 3s

echo -e "\033[31m Start Spark-related Process: \033[0m"
$DBNS_HOME/sbin/cluster/start-spark.sh
sleep 3s

echo -e "\033[31m Start Hive-related Process: \033[0m"
$DBNS_HOME/sbin/cluster/start-hive.sh
sleep 5s

echo -e "\033[31m Start Kafka-related Process: \033[0m"
$DBNS_HOME/sbin/cluster/start-kafka.sh
sleep 3s

echo -e "\033[31m Current JAVA Process: \033[0m"
jps
