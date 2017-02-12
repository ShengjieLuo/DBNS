$SPARK_HOME/sbin/stop-all.sh
sudo kill -9 `ps -aux | grep thrift | awk '{print $2}'`
