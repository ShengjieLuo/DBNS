sudo kill -9 `ps -aux | grep LogProducer | awk '{print $2}'`
sudo kill -9 `ps -aux | grep LogStreaming | awk '{print $2}'`
sudo kill -9 `ps -aux | grep run.py | awk '{print $2}'`

