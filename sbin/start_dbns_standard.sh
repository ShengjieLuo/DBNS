source env.rc
python $DBNS_HOME/script/cleanSQL.py
nohup run/runSenderLab.sh &
nohup run/runAnalyzerLab.sh &
nohup python run/run.py &
