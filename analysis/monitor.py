import mysql.connector
import datetime
import sys

#######################################################
#          To execute the SQL statement               #
#Note:                                                #
#1. Qualified for any kind of SQL statement           #
#2. Used for simple mysql operation by python         #
#######################################################
def exeSQL(sql):
	config = {
        	  'user':'root', 
	          'password':'123456', 
	          'host':'172.16.0.104', 
	          'port':3306,  
	          'database':'stat'}
	conn = mysql.connector.connect(**config)
	cur = conn.cursor()
	cur.execute(sql)
	conn.commit()
        cur.close()
	conn.close()
	return 1

#######################################################
#          To execute the SQL statement               #
#Note:                                                #
#1. Only used for query statement                     #
#2. Used for simple mysql operation by python         #
#######################################################
def exeSQLquery(sql):
	config = {
        	  'user':'root', 
	          'password':'123456', 
	          'host':'172.16.0.104', 
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

#######################################################
#          To execute the one minute batch            #
#Note:                                                #
#One minute batch executed each minute, the web.on**  #
#is the target SQL table, old table would be truncated#
#to provide the space for new tables. Only the latest #
#table would be recorded                              #
#######################################################
def oneMinuteBatch(removeOld):
	
	#print "Begin one Minute Batch"
	removeOld = int(removeOld)
	now = datetime.datetime.now()
	delta = datetime.timedelta(minutes=1)
	target = (now-delta).strftime('%Y-%m-%d %H:%M')
	#target = "2017-01-05 08:42:10"

	#print "Begin to truncate old batch"
	if removeOld!=0:
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
	
	#print "Begin to add new batch"
        exeSQL("insert into web.onHRSips(id,IPSource,count)select * from HRSips where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onHRSipd(id,IPDest,count)select * from HRSipd where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onHRSps(id,PortSource,count)select * from HRSps where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onHRSpd(id,PortDest,count)select * from HRSpd where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onHRSrc(id,returnCode,count)select * from HRSrc where id=\"" + target + "\" order by count DESC LIMIT 10;")

	exeSQL("insert into web.onHRQips(id,IPSource,count)select * from HRQips where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onHRQipd(id,IPDest,count)select * from HRQipd where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onHRQps(id,PortSource,count)select * from HRQps where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onHRQpd(id,PortDest,count)select * from HRQpd where id=\"" + target + "\" order by count DESC LIMIT 10;")

	exeSQL("insert into web.onDRSips(id,IPSource,count)select * from DRSips where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onDRSipd(id,IPDest,count)select * from DRSipd where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onDRSname(id,name,count)select * from DRSname where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onDRStype(id,type,count)select * from DRStype where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onDRSurl(id,url,count)select * from DRSurl where id=\"" + target + "\" order by count DESC LIMIT 10;")

	exeSQL("insert into web.onDRQips(id,IPSource,count)select * from DRQips where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onDRQipd(id,IPDest,count)select * from DRQipd where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onDRQname(id,name,count)select * from DRQname where id=\"" + target + "\" order by count DESC LIMIT 10;")
	exeSQL("insert into web.onDRQtype(id,type,count)select * from DRQtype where id=\"" + target + "\" order by count DESC LIMIT 10;")	

	#print "One-Minute Batch End!"

#######################################################
#          To execute the one hour batch              #
#Note:                                                #
#1. One-Hour-Batch(OHB) is executed every 3600sec. The#
#statistical data include the 3600sec field and writ- #
#ten to the web.of** table. The items in new table is #
#ordered with large to little                         #
#2. Use para removeOld(bool) to decide whether you    #
#need remove old batch                                #
#3. Example: Time: 14:01 Batch: 13:00--13:59          #
#######################################################
def oneHourBatch(removeOld):
	
	#print "One-Hour Batch begin!"
	now = datetime.datetime.now()
	delta = datetime.timedelta(minutes=60)
	target = (now-delta).strftime('%Y-%m-%d %H')
	judge = int(now.strftime('%M'))
	if judge != 0 and judge != 1:
		return 0
	removeOld = int(removeOld)
	#print "removeOld: "+str(removeOld)
                

	if removeOld!=0:
		print "Begin to truncate data!"
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

	#print "Begin to add data!"
        exeSQL("INSERT INTO web.ofHRSips(IPSource,count) SELECT IPSource,sum(count) as count FROM HRSips WHERE id LIKE \"" + target + "%\" GROUP BY IPSource ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRSipd(IPDest,count) SELECT IPDest,sum(count) as count FROM HRSipd WHERE id LIKE \"" + target + "%\" GROUP BY IPDest ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRSps(PortSource,count) SELECT PortSource,sum(count) as count FROM HRSps WHERE id LIKE \"" + target + "%\" GROUP BY PortSource ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRSpd(PortDest,count) SELECT PortDest,sum(count) as count FROM HRSpd WHERE id LIKE \"" + target + "%\" GROUP BY PortDest ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRSrc(returnCode,count) SELECT returnCode,sum(count) as count FROM HRSrc WHERE id LIKE \"" + target + "%\" GROUP BY returnCode ORDER BY count DESC LIMIT 30; ")

        exeSQL("INSERT INTO web.ofHRQips(IPSource,count) SELECT IPSource,sum(count) as count FROM HRQips WHERE id LIKE \"" + target + "%\" GROUP BY IPSource ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRQipd(IPDest,count) SELECT IPDest,sum(count) as count FROM HRQipd WHERE id LIKE \"" + target + "%\" GROUP BY IPDest ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRQps(PortSource,count) SELECT PortSource,sum(count) as count FROM HRQps WHERE id LIKE \"" + target + "%\" GROUP BY PortSource ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofHRQpd(PortDest,count) SELECT PortDest,sum(count) as count FROM HRQpd WHERE id LIKE \"" + target + "%\" GROUP BY PortDest ORDER BY count DESC LIMIT 30;")

        exeSQL("INSERT INTO web.ofDRSips(IPSource,count) SELECT IPSource,sum(count) as count FROM DRSips WHERE id LIKE \"" + target + "%\" GROUP BY IPSource ORDER BY count DESC LIMIT 30; ")
        exeSQL("INSERT INTO web.ofDRSipd(IPDest,count) SELECT IPDest,sum(count) as count FROM DRSipd WHERE id LIKE \"" + target + "%\" GROUP BY IPDest ORDER BY count DESC LIMIT 30; ")
	exeSQL("INSERT INTO web.ofDRSname(name,count) SELECT name,sum(count) as count FROM DRSname WHERE id LIKE \"" + target + "%\" GROUP BY name ORDER BY count DESC LIMIT 30; ")
	exeSQL("INSERT INTO web.ofDRStype(type,count) SELECT type,sum(count) as count FROM DRStype WHERE id LIKE \"" + target + "%\" GROUP BY type ORDER BY count DESC LIMIT 30; ")
	exeSQL("INSERT INTO web.ofDRSurl(url,count) SELECT url,sum(count) as count FROM DRSurl WHERE id LIKE \"" + target + "%\" GROUP BY url ORDER BY count DESC LIMIT 30; ")

	exeSQL("INSERT INTO web.ofDRQips(IPSource,count) SELECT IPSource,sum(count) as count FROM DRQips WHERE id LIKE \"" + target + "%\" GROUP BY IPSource ORDER BY count DESC LIMIT 30; ")
	exeSQL("INSERT INTO web.ofDRQipd(IPDest,count) SELECT IPDest,sum(count) as count FROM DRQipd WHERE id LIKE \"" + target + "%\" GROUP BY IPDest ORDER BY count DESC LIMIT 30; ")
	exeSQL("INSERT INTO web.ofDRQname(name,count) SELECT name,sum(count) as count FROM DRQname WHERE id LIKE \"" + target + "%\" GROUP BY name ORDER BY count DESC LIMIT 30; ")
	exeSQL("INSERT INTO web.ofDRQtype(type,count) SELECT type,sum(count) as count FROM DRQtype WHERE id LIKE \"" + target + "%\" GROUP BY type ORDER BY count DESC LIMIT 30; ")


        exeSQL("update web.ofHRSips set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofHRSipd set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofHRSps set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofHRSpd set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofHRSrc set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofHRQips set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofHRQipd set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofHRQps set id=\"" + target + ":00\"  WHERE id is NULL;")
        exeSQL("update web.ofHRQpd set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofDRSips set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofDRSipd set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofDRSname set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofDRStype set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofDRSurl set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofDRQips set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofDRQipd set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofDRQname set id=\"" + target + ":00\" WHERE id is NULL;")
        exeSQL("update web.ofDRQtype set id=\"" + target + ":00\" WHERE id is NULL;")
	#print "One day batch end!"

#######################################################
#          To execute the one day batch               #
#Note:                                                #
#One-Day-Batch(ODB) is executed every 86400sec. The   #
#operation is to truncate the existed table. The rea- #
#son why we use the ODB is to save the space and only #
#provide the data required by WebUI                   #
#######################################################
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
	if sys.argv[1] == "minute" or sys.argv[1]=="1":
		oneMinuteBatch(sys.argv[2])
	elif sys.argv[1] == "hour" or sys.argv[1]=="2":
		oneHourBatch(sys.argv[2])
	elif sys.argv[1] == "day" or sys.argv[1] == "3":
		oneDayBatch()
	else:
		print "[Error] Please use right parameter in monitor.py!"
