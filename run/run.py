import os
import time
import thread

def streamingTask(args):
	os.system("/usr/local/spark/bin/spark-submit --class \"LogStreaming\" /usr/local/spark/mycode/DBNSv02/target/scala-2.10/simple-project_2.10-1.0.jar --jars /usr/local/spark/mysql-connector-java-5.1.40/mysql-connector-java-5.1.40-bin.jar")

def HbaseTask(args):
	count = 0
	while count<10 :
		count += 1
		os.system("/usr/local/spark/bin/spark-submit --class \"Off2HBase\" /usr/local/spark/mycode/DBNSv02/target/scala-2.10/simple-project_2.10-1.0.jar")
	        time.sleep(60)

args = 1
thread.start_new_thread(streamingTask,(args,))
thread.start_new_thread(HbaseTask,(args,))
while 1:
   pass
