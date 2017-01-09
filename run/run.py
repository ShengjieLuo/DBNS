import os
import time
import thread
import sched
 
def perform_command(schedule,cmd, inc):
	schedule.enter(inc, 0, perform_command, (schedule,cmd, inc))
	os.system(cmd)

def timming_exe(schedule,cmd, inc = 60):
	schedule.enter(inc, 0, perform_command, (schedule,cmd, inc))
	schedule.run()

def HbaseTask(args):
	count = 0
	while count<10 :
		count += 1
		os.system("/usr/local/spark/bin/spark-submit --class \"Off2HBase\" /usr/local/spark/mycode/DBNSv02/target/scala-2.10/simple-project_2.10-1.0.jar")
	        time.sleep(60)

def LogAnalyzer(args):
	print "$DBNS_HOME/run/runAnalyzer.sh"
	os.system("$DBNS_HOME/run/runAnalyzer.sh")

def LogProducer(args):
	print "$DBNS_HOME/run/runSender.sh"
	os.system("$DBNS_HOME/run/runSender.sh")

def MinuteBatch(args):
	schedule = sched.scheduler(time.time, time.sleep)
	timming_exe(schedule,"python $DBNS_HOME/analysis/monitor.py minute", 60)

def HourBatch(args):
	schedule = sched.scheduler(time.time, time.sleep)
	timming_exe(schedule,"python $DBNS_HOME/analysis/monitor.py hour", 10)
	
def DayBatch(args):
	schedule = sched.scheduler(time.time, time.sleep)
	timming_exe(schedule,"python $DBNS_HOME/analysis/monitor.py day", 86400)


def main():
	args = 1
	#thread.start_new_thread(HbaseTask,(args,))
	#thread.start_new_thread(LogAnalyzer,(args,))
	#thread.start_new_thread(LogProducer,(args,))
	thread.start_new_thread(MinuteBatch,(args,))
	thread.start_new_thread(HourBatch,(args,))
	thread.start_new_thread(DayBatch,(args,))
	while 1:
		pass

if __name__ =="__main__":
	main()
