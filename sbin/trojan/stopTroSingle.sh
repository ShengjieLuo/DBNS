sudo kill -9 `ps -aux | grep Tro | awk '{print $2}'`
sudo kill -9 `ps -aux | grep Broker | awk '{print $2}'`
