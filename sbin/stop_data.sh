source env.rc
sudo kill -9 `ps -aux | grep LogProducer | awk '{print $2}'`
sudo kill -9 `ps -aux | grep LogStreaming | awk '{print $2}'`
sudo kill -9 `ps -aux | grep run.py | awk '{print $2}'`
python $DBNS_HOME/script/cleanSQL.py
rm -rf $DBNS_HOME/backup
rm -rf $DBNS_HOME/checkpoint
mkdir $DBNS_HOME/backup
mkdir $DBNS_HOME/checkpoint
rm nohup.out
rm derby.log
