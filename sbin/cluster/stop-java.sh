echo  -e "\033[47,31m******************************************* \033[0m"
echo  -e "\033[47,31m**         Welcome to use DBNS!          ** \033[0m"
echo  -e "\033[47,31m** This script is used to stop DBNS      ** \033[0m"
echo  -e "\033[47,31m** 1. Stop running java Process          ** \033[0m"
echo  -e "\033[47,31m** Shanghai Jiao Tong University         ** \033[0m"
echo  -e "\033[47,31m** Network Computing Lab                 ** \033[0m"
echo  -e "\033[47,31m** https://github.com/ShengjieLuo/DBNS   ** \033[0m"
echo  -e "\033[47,31m******************************************* \033[0m"

echo  -e "\033[31m******************************************* \033[0m"
echo  -e "\033[31m**         Stop running JAVA Process     ** \033[0m"
echo  -e "\033[31m******************************************* \033[0m"

echo -e "\033[31m Stop Spark-related Process: \033[0m"
$DBNS_HOME/sbin/cluster/stop-spark.sh
sleep 3s

echo -e "\033[31m Stop Hadoop-related Process: \033[0m"
$DBNS_HOME/sbin/cluster/stop-hadoop.sh
sleep 3s

echo -e "\033[31m Stop Kafka-related Process: \033[0m"
$DBNS_HOME/sbin/cluster/stop-kafka.sh
sleep 3s

echo -e "\033[31m Stop Hive-related Process: \033[0m"
$DBNS_HOME/sbin/cluster/stop-hive.sh
sleep 3s

echo -e "\033[31m Remain JAVA Process: \033[0m"
jps
sleep 3s
