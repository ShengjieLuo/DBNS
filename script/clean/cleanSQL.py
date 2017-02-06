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

if __name__=="__main__":
	truncateTable()
