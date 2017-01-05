import mysql.connector
import datetime

def exeSQLinsert(sql):
	config = {
        	  'user':'root', 
	          'password':'123456', 
	          'host':'127.0.0.1', 
	          'port':3306,  
	          'database':'stat'}
	conn = mysql.connector.connect(**config)
	cur = conn.cursor()
	cur.execute(sql)
	conn.commit()
        cur.close()
	conn.close()
	return 1

def exeSQLinsert(sql):
	config = {
        	  'user':'root', 
	          'password':'123456', 
	          'host':'127.0.0.1', 
	          'port':3306,  
	          'database':'stat'}
	conn = mysql.connector.connect(**config)
	cur = conn.cursor()
	cur.execute(sql)
	result_set = cur.fetchall()
	'''
	if result_set:
		for row in result_set:
			print "%s, %s, %d" % (row[0],row[1],row[2])
	'''
        cur.close()
	conn.close()
	return result_set

def oneMinuteBatch():
	now = datetime.datetime.now()
	delta = datetime.timedelta(minutes=1)
	target = (now-delta).strftime('%Y-%m-%d %H:%M')
	exeSQL("insert into web.onHRSips(id,IPSource,count)select * from HRSips where id=\"" + target + "\";")
	exeSQL("insert into web.onHRSipd(id,IPDest,count)select * from HRSipd where id=\"" + target + "\";")
	exeSQL("insert into web.onHRSps(id,PortSource,count)select * from HRSps where id=\"" + target + "\";")
	exeSQL("insert into web.onHRSpd(id,PortDest,count)select * from HRSpd where id=\"" + target + "\";")
	exeSQL("insert into web.onHRSrc(id,returnCode,count)select * from HRSrc where id=\"" + target + "\";")

	exeSQL("insert into web.onHRQips(id,IPSource,count)select * from HRQips where id=\"" + target + "\";")
	exeSQL("insert into web.onHRQipd(id,IPDest,count)select * from HRQipd where id=\"" + target + "\";")
	exeSQL("insert into web.onHRQps(id,PortSource,count)select * from HRQps where id=\"" + target + "\";")
	exeSQL("insert into web.onHRQpd(id,PortDest,count)select * from HRQpd where id=\"" + target + "\";")

	exeSQL("insert into web.onDRSips(id,IPSource,count)select * from DRSips where id=\"" + target + "\";")
	exeSQL("insert into web.onDRSipd(id,IPDest,count)select * from DRSipd where id=\"" + target + "\";")
	exeSQL("insert into web.onDRSname(id,name,count)select * from DRSname where id=\"" + target + "\";")
	exeSQL("insert into web.onDRStype(id,type,count)select * from DRStype where id=\"" + target + "\";")
	exeSQL("insert into web.onDRSurl(id,url,count)select * from DRSurl where id=\"" + target + "\";")

	exeSQL("insert into web.onDRQips(id,IPSource,count)select * from DRQips where id=\"" + target + "\";")
	exeSQL("insert into web.onDRQipd(id,IPDest,count)select * from DRQipd where id=\"" + target + "\";")
	exeSQL("insert into web.onDRQname(id,name,count)select * from DRQname where id=\"" + target + "\";")
	exeSQL("insert into web.onDRQtype(id,type,count)select * from DRQtype where id=\"" + target + "\";")	

def oneHourBatch():
	pass

def oneDayBatch():
	pass

if __name__=="__main__":
	exeSQL("insert into web.onDRQipd(id,IPDest,count)select * from DRQipd where id=\"2017-01-05 08:42:10\";")
