source env.rc
python $DBNS_HOME/script/cleanSQL.py
nohup run/runSenderCluster.sh &
nohup run/runAnalyzerCluster.sh &
nohup python run/run.py &
