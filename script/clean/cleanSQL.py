import mysql.connector

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
	          'host':'spark-master', 
	          'port':3306,  
	          'database':'stat'}
	conn = mysql.connector.connect(**config)
	cur = conn.cursor()
	cur.execute(sql)
	conn.commit()
        cur.close()
	conn.close()
	return 1

def truncateTable():

	
	exeSQL("TRUNCATE stat.HRSips")
        exeSQL("TRUNCATE stat.HRSipd")
        exeSQL("TRUNCATE stat.HRSps")
        exeSQL("TRUNCATE stat.HRSpd")
        exeSQL("TRUNCATE stat.HRSrc")
        exeSQL("TRUNCATE stat.HRQips")
        exeSQL("TRUNCATE stat.HRQipd")
        exeSQL("TRUNCATE stat.HRQps")
        exeSQL("TRUNCATE stat.HRQpd")
        exeSQL("TRUNCATE stat.DRSips")
        exeSQL("TRUNCATE stat.DRSipd")
        exeSQL("TRUNCATE stat.DRSname")
        exeSQL("TRUNCATE stat.DRStype")
        exeSQL("TRUNCATE stat.DRSurl")
        exeSQL("TRUNCATE stat.DRQips")
        exeSQL("TRUNCATE stat.DRQipd")
        exeSQL("TRUNCATE stat.DRQname")
        exeSQL("TRUNCATE stat.DRQtype")
	
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

	exeSQL("truncate table DBNS.HRSips")
	exeSQL("truncate table DBNS.HRSipd")
	exeSQL("truncate table DBNS.HRSps")
	exeSQL("truncate table DBNS.HRSpd")
	exeSQL("truncate table DBNS.HRSrc")
	exeSQL("truncate table DBNS.HRSipss")
	exeSQL("truncate table DBNS.HRSipds")
	exeSQL("truncate table DBNS.HRSsum")
	exeSQL("truncate table DBNS.HRScount")

	exeSQL("truncate table DBNS.HRQips")
	exeSQL("truncate table DBNS.HRQipd")
	exeSQL("truncate table DBNS.HRQps")
	exeSQL("truncate table DBNS.HRQpd")
	exeSQL("truncate table DBNS.HRQipss")
	exeSQL("truncate table DBNS.HRQipds")
	exeSQL("truncate table DBNS.HRQsum")
	exeSQL("truncate table DBNS.HRQcount")
	
	exeSQL("truncate table DBNS.DRSips")
	exeSQL("truncate table DBNS.DRSipd")
	exeSQL("truncate table DBNS.DRSname")
	exeSQL("truncate table DBNS.DRStype")
	exeSQL("truncate table DBNS.DRSurl")
	exeSQL("truncate table DBNS.DRScount")

	exeSQL("truncate table DBNS.DRQips")
	exeSQL("truncate table DBNS.DRQipd")
	exeSQL("truncate table DBNS.DRQname")
	exeSQL("truncate table DBNS.DRQtype")
	exeSQL("truncate table DBNS.DRQcount")

	exeSQL("truncate table DBNS.offstatus")
	exeSQL("truncate table DBNS.onstatus")
	exeSQL("truncate table DBNS.latest")

	exeSQL("insert into DBNS.offstatus values (0,\"DRQ\",0,0)")
	exeSQL("insert into DBNS.offstatus values (0,\"HRQ\",0,0)")
	exeSQL("insert into DBNS.offstatus values (0,\"HRS\",0,0)")
	exeSQL("insert into DBNS.offstatus values (0,\"DRS\",0,0)")
	exeSQL("insert into DBNS.latest values (0)")


if __name__=="__main__":
	truncateTable()
