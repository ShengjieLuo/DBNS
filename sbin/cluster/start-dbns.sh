echo  -e "\033[47,31m******************************************* \033[0m"
echo  -e "\033[47,31m**         Welcome to use DBNS!          ** \033[0m"
echo  -e "\033[47,31m** This script is used to start DBNS   ** \033[0m"
echo  -e "\033[47,31m** 1. Source Environment Var             ** \033[0m"
echo  -e "\033[47,31m** 2. Execute DBNS Task                  ** \033[0m"
echo  -e "\033[47,31m** 3. DBNS Launch Conclusion             ** \033[0m"
echo  -e "\033[47,31m** Shanghai Jiao Tong University         ** \033[0m"
echo  -e "\033[47,31m** Network Computing Lab                 ** \033[0m"
echo  -e "\033[47,31m** https://github.com/ShengjieLuo/DBNS   ** \033[0m"
echo  -e "\033[47,31m******************************************* \033[0m"

echo  -e "\033[31m******************************************* \033[0m"
echo  -e "\033[31m**         Source Environment Var        ** \033[0m"
echo  -e "\033[31m******************************************* \033[0m"
source /usr/local/DBNS/env.rc
echo  "Spark Home:",$SPARK_HOME
echo  "Hadoop Home:",$HADOOP_HOME
echo  "Hive Home:",$HIVE_HOME
echo  "Kafka Home:",$KAFKA_HOME
echo  "DBNS Home:",$DBNS_HOME
echo  "DBNS logs:",$DBNS_LOG

echo  -e "\033[31m******************************************* \033[0m"
echo  -e "\033[31m**         Execute DBNS Task             ** \033[0m"
echo  -e "\033[31m******************************************* \033[0m"

echo  -e "\033[31m Send Network Package       ......begin \033[0m"
nohup $DBNS_HOME/run/runSenderCluster.sh 2>$DBNS_LOG/Sender.err 1>$DBNS_LOG/Sender.log &
sleep 3s
echo  -e "\033[31m Send Network Package       ......success \033[0m"

echo  -e "\033[31m Launch Streaming Analyzer  ......begin \033[0m"
nohup $DBNS_HOME/run/runAnalyzerCluster.sh 2>$DBNS_LOG/Analyzer.err 1>$DBNS_LOG/Analyzer.log &
sleep 3s
echo  -e "\033[31m Launch Streaming Analyzer  ......success \033[0m"

echo  -e "\033[31m Launch Network Monitor     ......begin \033[0m"
nohup python $DBNS_HOME/run/run.py 2>$DBNS_LOG/Monitor.err 1>$DBNS_LOG/Monitor.log &
sleep 3s
echo  -e "\033[31m Launch Network Monitor     ......success \033[0m"

echo  -e "\033[31m Launch Offline Batch     ......begin \033[0m"
nohup service cron start 2>$DBNS_LOG/Offline.err 1>$DBNS_LOG/Offline.log
sleep 3s
echo  -e "\033[31m Launch Offline Batch     ......success \033[0m"

echo  -e "\033[31m******************************************* \033[0m"
echo  -e "\033[31m**         DBNS Launch Conclusion        ** \033[0m"
echo  -e "\033[31m******************************************* \033[0m"
echo "Sender Log:" $DBNS_LOG/Sender.log
echo "Analyzer Log:" $DBNS_LOG/Analyzer.log
echo "Monitor Log:" $DBNS_LOG/Monitor.log
echo "Sender Error:" $DBNS_LOG/Sender.err
echo "Analyzer Error:" $DBNS_LOG/AnalyzerErr.err
echo "Monitor Error:" $DBNS_LOG/MonitorErr.err
echo  -e "\033[31m******************************************* \033[0m"
