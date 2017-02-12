sudo kill -9 `ps -aux | grep metastore | awk '{print $2}'`
sudo kill -9 `ps -aux | grep HiveServer2 | awk '{print $2}'`
