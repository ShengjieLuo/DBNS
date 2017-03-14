echo  -e "\033[47,31m******************************************* \033[0m"
echo  -e "\033[47,31m**         Welcome to use DBNS!          ** \033[0m"
echo  -e "\033[47,31m** This script is used to stop DBNS      ** \033[0m"
echo  -e "\033[47,31m** 1. Stop running DBNS Process          ** \033[0m"
echo  -e "\033[47,31m** 2. Clean Previous Data                ** \033[0m"
echo  -e "\033[47,31m** Shanghai Jiao Tong University         ** \033[0m"
echo  -e "\033[47,31m** Network Computing Lab                 ** \033[0m"
echo  -e "\033[47,31m** https://github.com/ShengjieLuo/DBNS   ** \033[0m"
echo  -e "\033[47,31m******************************************* \033[0m"

echo  -e "\033[31m******************************************* \033[0m"
echo  -e "\033[31m**         Stop running DBNS Process     ** \033[0m"
echo  -e "\033[31m******************************************* \033[0m"

echo -e "\033[31m Kill Netowrk Package Sender Process: \033[0m"
ps -aux | grep '\-\-class LogProducer'
sudo kill -9 `ps -aux | grep LogProducer | awk '{print $2}'`

echo -e "\033[31m Kill Streaming Analyzer Process: \033[0m"
ps -aux | grep '\-\-class LogStreaming' 
sudo kill -9 `ps -aux | grep LogStreaming | awk '{print $2}'`

echo -e "\033[31m Kill Security Monitor Process: \033[0m"
ps -aux | grep 'python\ run/run.py'
sudo kill -9 `ps -aux | grep run.py | awk '{print $2}'`


echo -e "\033[31m Kill Offline Batch: \033[0m"
service cron stop

echo  -e "\033[31m******************************************* \033[0m"
echo  -e "\033[31m**         Clean Previous Data           ** \033[0m"
echo  -e "\033[31m******************************************* \033[0m"

echo  -e "\033[31m Clean data in mysql database......begin \033[0m"
python $DBNS_HOME/script/clean/cleanSQL.py
echo  -e "\033[31m Clean data in mysql database......done \033[0m"

echo  -e "\033[31m Clean data in hive database......begin \033[0m"
beeline -u jdbc:hive2://spark-master:10000 -n root -p 123456  -f $DBNS_HOME/script/clean/cleanHive.sql
echo  -e "\033[31m Clean data in hive database......done \033[0m"

echo  -e "\033[31m Clean logs                 ......begin \033[0m"
rm -f $DBNS_LOG/*
rm -f $DBNS_HOME/derby.log
echo  -e "\033[31m Clean logs                 ......done \033[0m"
