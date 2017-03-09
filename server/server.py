#coding=utf-8
from flask import Flask, request, render_template
import os
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from flaskext.mysql import MySQL

mysql = MySQL()
app = Flask(__name__)

app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = '123456'
app.config['MYSQL_DATABASE_DB'] = 'web'
app.config['MYSQL_DATABASE_HOST'] = 'spark-master'

mysql.init_app(app)

@app.route('/<name>/<famliy>/<start>/<end>')
def showbookname(name,famliy,start,end):
	cursor = mysql.get_db().cursor()

	# Stage1: Verfication
	if name == "http_request" or name == "http_response" or name == "dns_response" or name == "dns_request":
		pass
	else:
		return render_template('test.html', obj = "Wrong Table Name")

	if famliy == "top10-1h" or famliy == "throughput":
		pass
	else:
		return render_template('test.html', obj = "Wrong Statistical Item!")
	
	try:
		start = int(start)
		end = int(end)
	except:
		return render_template('test.html', obj = "Wrong start/end time! Please use a number!")

	# Stage2: Revise the SQL command
	if name == "http_request":
		sqltable = "HRQ"
	elif name == "http_response":
		sqltable = "HRS"
	elif name == "dns_response":
		sqltable = "DRS"
	elif name == "dns_request":
		sqltable = "DRQ"

	#sqlstart = 2600
        sqlstart = start+3600-start%3600
        sqltime = []
	if (sqlstart<end):
		sqltime.append(str(sqlstart))
		sqlnew = sqlstart
		while sqlnew+3600 < end:
			sqlnew = sqlnew + 3600
			sqltime.append(str(sqlnew))
	print sqltime
	
	DRQips , DRQipd , DRQurl = [],[],[]
	DRSips , DRSipd , DRSurl = [],[],[]
	HRQips , HRQipd , HRQipss , HRQipds = [],[],[],[]
	HRSips , HRSipd , HRSipss , HRSipds = [],[],[],[]
	DRQcount , DRScount =[],[]
	HRQcount , HRQsize = [],[]
	HRScount , HRSsize = [],[]

	for time in sqltime:
		print name,famliy
		if name == "http_request" and famliy == "top10-1h":
			command = "SELECT * FROM DBNS."+sqltable+"ips WHERE time= "+time+" LIMIT 10"
			print command
			cursor.execute(command)
			HRQips.append(cursor.fetchall())
			
			command = "SELECT * FROM DBNS."+sqltable+"ipd WHERE time= "+time+" LIMIT 10"
			print command
			cursor.execute(command)
			HRQipd.append(cursor.fetchall())
			
			command = "SELECT * FROM DBNS."+sqltable+"ipss WHERE time= "+time+" LIMIT 10"
			print command
			cursor.execute(command)
			HRQipss.append(cursor.fetchall())
			
			command = "SELECT * FROM DBNS."+sqltable+"ipds WHERE time= "+time+" LIMIT 10"
			print command
			cursor.execute(command)
			HRQipds.append(cursor.fetchall())
		
		elif name == "http_response" and famliy == "top10-1h":
			command = "SELECT * FROM DBNS."+sqltable+"ips WHERE time= "+time+" LIMIT 10"
			cursor.execute(command)
			HRSips.append(cursor.fetchall())
			
			command = "SELECT * FROM DBNS."+sqltable+"ipd WHERE time= "+time+" LIMIT 10"
			cursor.execute(command)
			HRSipd.append(cursor.fetchall())
			
			command = "SELECT * FROM DBNS."+sqltable+"ipss WHERE time= "+time+" LIMIT 10"
			cursor.execute(command)
			HRSipss.append(cursor.fetchall())
			
			command = "SELECT * FROM DBNS."+sqltable+"ipds WHERE time= "+time+" LIMIT 10"
			cursor.execute(command)
			HRSipds.append(cursor.fetchall())
		
		elif name == "dns_response" and famliy == "top10-1h":
			command = "SELECT * FROM DBNS."+sqltable+"ips WHERE time= "+time+" LIMIT 10"
			cursor.execute(command)
			DRSips.append(cursor.fetchall())
		
			command = "SELECT * FROM DBNS."+sqltable+"ipd WHERE time= "+time+" LIMIT 10"
			cursor.execute(command)
			DRSipd.append(cursor.fetchall())
			
			command = "SELECT * FROM DBNS."+sqltable+"name WHERE time= "+time+" LIMIT 10"
			cursor.execute(command)
			DRSurl.append(cursor.fetchall())

		elif name == "dns_request" and famliy == "top10-1h":
			command = "SELECT * FROM DBNS."+sqltable+"ips WHERE time= "+time+" LIMIT 10"
			cursor.execute(command)
			DRQips.append(cursor.fetchall())
		
			command = "SELECT * FROM DBNS."+sqltable+"ipd WHERE time= "+time+" LIMIT 10"
			cursor.execute(command)
			DRQipd.append(cursor.fetchall())
			
			command = "SELECT * FROM DBNS."+sqltable+"name WHERE time= "+time+" LIMIT 10"
			cursor.execute(command)
			DRQurl.append(cursor.fetchall())
		
		elif name == "http_request:" and famliy == "throughput":
			command = "SELECT * FROM DBNS."+sqltable+"count WHERE time= "+time+" LIMIT 10"
			cursor.execute(command)
			HRQcount.append(cursor.fetchall())
			
			command = "SELECT * FROM DBNS."+sqltable+"sum WHERE time= "+time+" LIMIT 10"
			cursor.execute(command)
			HRQsum.append(cursor.fetchall())
			
		elif name == "http_response:" and famliy == "throughput":
			command = "SELECT * FROM DBNS."+sqltable+"count WHERE time= "+time
			cursor.execute(command)
			HRScount.append(cursor.fetchall())
			
			command = "SELECT * FROM DBNS."+sqltable+"sum WHERE time= "+time
			cursor.execute(command)
			HRSsum.append(cursor.fetchall())
					
		elif name == "dns_response:" and famliy == "throughput":
			command = "SELECT * FROM DBNS."+sqltable+"count WHERE time= "+time
			cursor.execute(command)
			DRScount.append(cursor.fetchall())

		elif name == "dns_request:" and famliy == "throughput":
			command = "SELECT * FROM DBNS."+sqltable+"count WHERE time= "+time
			cursor.execute(command)
			DRQcount.append(cursor.fetchall())

	'''Debug
	print "DRQ",DRQips,DRQipd,DRQurl,DRQcount
	print "DRS",DRSips,DRSipd,DRSurl,DRScount
        print "HRQ",HRQips,HRQipd,HRQipss,HRQipds,HRQcount,HRQsize
        print "HRS",HRSips,HRSipd,HRSipss,HRSipds,HRScount,HRSsize
	'''	

	obj = "name:"+str(name)+" famliy:"+str(famliy)+" start:"+str(start)+" end:"+str(end)+"\n"
	return render_template('test.html', obj = obj)

if __name__ == '__main__':
	app.run(host='0.0.0.0', port=4321, debug = True)
