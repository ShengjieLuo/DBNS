sudo kill -9 `ps -aux | grep metastore | awk '{print $2}'`
