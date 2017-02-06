echo  -e "\033[47,31m******************************************* \033[0m"
echo  -e "\033[47,31m**         Welcome to use DBNS!          ** \033[0m"
echo  -e "\033[47,31m** This script is used to restart DBNS   ** \033[0m"
echo  -e "\033[47,31m** 1. Stop running DBNS Process          ** \033[0m"
echo  -e "\033[47,31m** 2. Source Environment Var             ** \033[0m"
echo  -e "\033[47,31m** 3. Clean Previous Data                ** \033[0m"
echo  -e "\033[47,31m** 4. Execute DBNS Task                  ** \033[0m"
echo  -e "\033[47,31m** 5. DBNS Launch Conclusion             ** \033[0m"
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


echo  -e "\033[31m******************************************* \033[0m"
echo  -e "\033[31m**         Source Environment Var        ** \033[0m"
echo  -e "\033[31m******************************************* \033[0m"
source env.rc
echo  "Spark Home:",$SPARK_HOME
echo  "Hadoop Home:",$HADOOP_HOME
echo  "Hive Home:",$HIVE_HOME
echo  "Kafka Home:",$KAFKA_HOME
echo  "DBNS Home:",$DBNS_HOME
echo  "DBNS logs:",$DBNS_LOG


echo  -e "\033[31m******************************************* \033[0m"
echo  -e "\033[31m**         Clean Previous Data           ** \033[0m"
echo  -e "\033[31m******************************************* \033[0m"

echo  -e "\033[31m Clean data in mysql database......begin \033[0m"
python $DBNS_HOME/script/clean/cleanSQL.py
echo  -e "\033[31m Clean data in mysql database......done \033[0m"

echo  -e "\033[31m Clean data in hive database......begin \033[0m"
beeline -u jdbc:hive2://172.16.0.104:10000 -n root -p 123456  -f $DBNS_HOME/script/clean/cleanHive.sql
echo  -e "\033[31m Clean data in hive database......done \033[0m"


echo  -e "\033[31m Clean logs                 ......begin \033[0m"
rm -f $DBNS_LOG/*
echo  -e "\033[31m Clean logs                 ......done \033[0m"

echo  -e "\033[31m******************************************* \033[0m"
echo  -e "\033[31m**         Execute DBNS Task             ** \033[0m"
echo  -e "\033[31m******************************************* \033[0m"

echo  -e "\033[31m Send Network Package       ......begin \033[0m"
nohup $DBNS_HOME/run/runSenderCluster.sh 2>$DBNS_LOG/SenderErr.txt 1>$DBNS_LOG/SenderLog.txt &
sleep 3s
echo  -e "\033[31m Send Network Package       ......success \033[0m"

echo  -e "\033[31m Launch Streaming Analyzer  ......begin \033[0m"
nohup $DBNS_HOME/run/runAnalyzerCluster.sh 2>$DBNS_LOG/AnalyzerErr.txt 1>$DBNS_LOG/AnalyzerLog.txt &
sleep 3s
echo  -e "\033[31m Launch Streaming Analyzer  ......success \033[0m"

echo  -e "\033[31m Launch Network Monitor     ......begin \033[0m"
nohup python $DBNS_HOME/run/run.py 2>$DBNS_LOG/MonitorErr.txt 1>$DBNS_LOG/MonitorLog.txt &
sleep 3s
echo  -e "\033[31m Launch Network Monitor     ......success \033[0m"

echo  -e "\033[31m******************************************* \033[0m"
echo  -e "\033[31m**         DBNS Launch Conclusion        ** \033[0m"
echo  -e "\033[31m******************************************* \033[0m"
echo "Sender Log:" $DBNS_LOG/SenderLog.txt
echo "Analyzer Log:" $DBNS_LOG/AnalyzerLog.txt
echo "Monitor Log:" $DBNS_LOG/MonitorLog.txt
echo "Sender Error:" $DBNS_LOG/SenderErr.txt
echo "Analyzer Error:" $DBNS_LOG/AnalyzerErr.txt
echo "Monitor Error:" $DBNS_LOG/MonitorErr.txt
echo  -e "\033[31m******************************************* \033[0m"
