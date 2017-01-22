source env.rc
python $DBNS_HOME/script/cleanSQL.py
nohup run/runSender.sh &
nohup run/runAnalyzer.sh &
nohup python run/run.py &
