$HADOOP_HOME/sbin/stop-all.sh
sudo kill -9 `ps -aux | grep JobHistoryServer  | awk '{print $2}'`
