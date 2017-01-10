<<<<<<< HEAD
source env.rc
sudo kill -9 `ps -aux | grep LogProducer | awk '{print $2}'`
sudo kill -9 `ps -aux | grep LogStreaming | awk '{print $2}'`
sudo kill -9 `ps -aux | grep run.py | awk '{print $2}'`
python $DBNS_HOME/script/cleanSQL.py
=======
sudo kill -9 `ps -aux | grep LogProducer | awk '{print $2}'`
sudo kill -9 `ps -aux | grep LogStreaming | awk '{print $2}'`
sudo kill -9 `ps -aux | grep run.py | awk '{print $2}'`

>>>>>>> 868d9d087c1c9bc94ab695a81ccbd900ac17a1a3
