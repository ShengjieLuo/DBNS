import mysql.connector
import datetime

def exeSQL(sql):
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

def exeSQLquery(sql):
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
	
	exeSQL("TRUNCATE web.onHRSips")
	exeSQL("TRUNCATE web.onHRSipd")
	exeSQL("TRUNCATE web.onHRSps")
	exeSQL("TRUNCATE web.onHRSpd")
	exeSQL("TRUNCATE web.onHRSrc")
	exeSQL("TRUNCATE web.onHRQips")
	exeSQL("TRUNCATE web.onHRQipd")
	exeSQL("TRUNCATE web.onHRQps")
	exeSQL("TRUNCATE web.onHRQpd")
	exeSQL("TRUNCATE web.onDRSips")
	exeSQL("TRUNCATE web.onDRSipd")
	exeSQL("TRUNCATE web.onDRSname")
	exeSQL("TRUNCATE web.onDRStype")
	exeSQL("TRUNCATE web.onDRSurl")
	exeSQL("TRUNCATE web.onDRQips")
	exeSQL("TRUNCATE web.onDRQipd")
	exeSQL("TRUNCATE web.onDRQname")
	exeSQL("TRUNCATE web.onDRQtype")
	
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
	now = datatime.datetime.now()
	delta = datetime.timedelta(minutes=10)
	target = (now-delta).strfttime('%Y-%m-%d %H')

	exeSQL("TRUNCATE web.ofHRSips")
        exeSQL("TRUNCATE web.ofHRSipd")
        exeSQL("TRUNCATE web.ofHRSps")
        exeSQL("TRUNCATE web.ofHRSpd")
        exeSQL("TRUNCATE web.ofHRSrc")
        exeSQL("TRUNCATE web.ofHRQips")
        exeSQL("TRUNCATE web.ofHRQipd")
        exeSQL("TRUNCATE web.ofHRQps")
        exeSQL("TRUNCATE web.ofHRQpd")
        exeSQL("TRUNCATE web.ofDRSips")
        exeSQL("TRUNCATE web.ofDRSipd")
        exeSQL("TRUNCATE web.ofDRSname")
        exeSQL("TRUNCATE web.ofDRStype")
        exeSQL("TRUNCATE web.ofDRSurl")
        exeSQL("TRUNCATE web.ofDRQips")
        exeSQL("TRUNCATE web.ofDRQipd")
        exeSQL("TRUNCATE web.ofDRQname")
        exeSQL("TRUNCATE web.ofDRQtype")

        exeSQL("INSERT INTO web.ofHRSips(IPSource,count) SELECT IPSource,count FROM HRSips WHERE id LIKE \"" + target + "%\" GROUP BY IPSource ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRSipd(IPDest,count) SELECT IPDest,count FROM HRSipd WHERE id LIKE \"" + target + "%\" GROUP BY IPDest ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRSps(PortSource,count) SELECT PortSource,count FROM HRSps WHERE id LIKE \"" + target + "%\" GROUP BY PortSource ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRSpd(PortDest,count) SELECT PortDest,count FROM HRSpd WHERE id LIKE \"" + target + "%\" GROUP BY PortDest ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRSrc(returnCode,count) SELECT returnCode,count FROM HRSrc WHERE id LIKE \"" + target + "%\" GROUP BY returnCode ORDER BY count DESC LIMIT 30; ")

        exeSQL("INSERT INTO web.ofHRQips(IPSource,count) SELECT IPSource,count FROM HRQips WHERE id LIKE \"" + target + "%\" GROUP BY IPSource ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRQipd(IPDest,count) SELECT IPDest,count FROM HRQipd WHERE id LIKE \"" + target + "%\" GROUP BY IPDest ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRQps(PortSource,count) SELECT PortSource,count FROM HRQps WHERE id LIKE \"" + target + "%\" GROUP BY PortSource ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRQpd(PortDest,count) SELECT PortDest,count FROM HRQpd WHERE id LIKE \"" + target + "%\" GROUP BY PortDest ORDER BY count DESC LIMIT 30;")

        exeSQL("INSERT INTO web.ofDRSips(IPSource,count) SELECT IPSource,count FROM DRSips WHERE id LIKE \"" + target + "%\" GROUP BY IPSource ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofDRSipd(IPDest,count) SELECT IPDest,count FROM DRSipd WHERE id LIKE \"" + target + "%\" GROUP BY IPDest ORDER BY count DESC LIMIT 30; ")
	exeSQL("INSERT INTO web.ofDRSname(name,count) SELECT name,count FROM DRSname WHERE id LIKE \"" + target + "%\" GROUP BY name ORDER BY count DESC LIMIT 30; ")
	exeSQL("INSERT INTO web.ofDRStype(type,count) SELECT type,count FROM DRStype WHERE id LIKE \"" + target + "%\" GROUP BY type ORDER BY count DESC LIMIT 30; ")
	exeSQL("INSERT INTO web.ofDRSurl(url,count) SELECT url,count FROM DRSurl WHERE id LIKE \"" + target + "%\" GROUP BY url ORDER BY count DESC LIMIT 30; ")

	exeSQL("INSERT INTO web.ofDRQips(IPSource,count) SELECT IPSource,count FROM DRQips WHERE id LIKE \"" + target + "%\" GROUP BY IPSource ORDER BY count DESC LIMIT 30; ")
	exeSQL("INSERT INTO web.ofDRQipd(IPDest,count) SELECT IPDest,count FROM DRQipd WHERE id LIKE \"" + target + "%\" GROUP BY IPDest ORDER BY count DESC LIMIT 30; ")
	exeSQL("INSERT INTO web.ofDRQname(name,count) SELECT name,count FROM DRQname WHERE id LIKE \"" + target + "%\" GROUP BY name ORDER BY count DESC LIMIT 30; ")
	exeSQL("INSERT INTO web.ofDRQtype(type,count) SELECT type,count FROM DRQtype WHERE id LIKE \"" + target + "%\" GROUP BY type ORDER BY count DESC LIMIT 30; ")


        exeSQL("update web.ofHRSips set id=\"" + target + ":00\";")
        exeSQL("update web.ofHRSipd set id=\"" + target + ":00\";")
        exeSQL("update web.ofHRSps set id=\"" + target + ":00\";")
        exeSQL("update web.ofHRSpd set id=\"" + target + ":00\";")
        exeSQL("update web.ofHRSrc set id=\"" + target + ":00\";")
        exeSQL("update web.ofHRQips set id=\"" + target + ":00\";")
        exeSQL("update web.ofHRQipd set id=\"" + target + ":00\";")
        exeSQL("update web.ofHRQps set id=\"" + target + ":00\";")
        exeSQL("update web.ofHRQpd set id=\"" + target + ":00\";")
        exeSQL("update web.ofDRSips set id=\"" + target + ":00\";")
        exeSQL("update web.ofDRSipd set id=\"" + target + ":00\";")
        exeSQL("update web.ofDRSname set id=\"" + target + ":00\";")
        exeSQL("update web.ofDRStype set id=\"" + target + ":00\";")
        exeSQL("update web.ofDRSurl set id=\"" + target + ":00\";")
        exeSQL("update web.ofDRQips set id=\"" + target + ":00\";")
        exeSQL("update web.ofDRQipd set id=\"" + target + ":00\";")
        exeSQL("update web.ofDRQname set id=\"" + target + ":00\";")
        exeSQL("update web.ofDRQtype set id=\"" + target + ":00\";")

def oneDayBatch():
	exeSQL("TRUNCATE web.onHRSips")
        exeSQL("TRUNCATE web.onHRSipd")
        exeSQL("TRUNCATE web.onHRSps")
        exeSQL("TRUNCATE web.onHRSpd")
        exeSQL("TRUNCATE web.onHRSrc")
        exeSQL("TRUNCATE web.onHRQips")
        exeSQL("TRUNCATE web.onHRQipd")
        exeSQL("TRUNCATE web.onHRQps")
        exeSQL("TRUNCATE web.onHRQpd")
        exeSQL("TRUNCATE web.onDRSips")
        exeSQL("TRUNCATE web.onDRSipd")
        exeSQL("TRUNCATE web.onDRSname")
        exeSQL("TRUNCATE web.onDRStype")
        exeSQL("TRUNCATE web.onDRSurl")
        exeSQL("TRUNCATE web.onDRQips")
        exeSQL("TRUNCATE web.onDRQipd")
        exeSQL("TRUNCATE web.onDRQname")
        exeSQL("TRUNCATE web.onDRQtype")

	exeSQL("TRUNCATE web.ofHRSips")
        exeSQL("TRUNCATE web.ofHRSipd")
        exeSQL("TRUNCATE web.ofHRSps")
        exeSQL("TRUNCATE web.ofHRSpd")
        exeSQL("TRUNCATE web.ofHRSrc")
        exeSQL("TRUNCATE web.ofHRQips")
        exeSQL("TRUNCATE web.ofHRQipd")
        exeSQL("TRUNCATE web.ofHRQps")
        exeSQL("TRUNCATE web.ofHRQpd")
        exeSQL("TRUNCATE web.ofDRSips")
        exeSQL("TRUNCATE web.ofDRSipd")
        exeSQL("TRUNCATE web.ofDRSname")
        exeSQL("TRUNCATE web.ofDRStype")
        exeSQL("TRUNCATE web.ofDRSurl")
        exeSQL("TRUNCATE web.ofDRQips")
        exeSQL("TRUNCATE web.ofDRQipd")
        exeSQL("TRUNCATE web.ofDRQname")
        exeSQL("TRUNCATE web.ofDRQtype")

if __name__=="__main__":
	exeSQL("insert into web.onDRQipd(id,IPDest,count)select * from DRQipd where id=\"2017-01-05 08:42:10\";")
