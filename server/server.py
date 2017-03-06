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

@app.route('/<table>/<famliy>/<start>/<end>')
def showbookname(name,famliy,start,end):
	cursor = mysql.get_db().cursor()

	# Stage1: Verfication
	if name == "http_request" or name == "http_response" or name == "dns_response" or name == "dns_request":
		continue
	else:
		return render_template('test.html', obj = "Wrong Table Name")

	if famliy == "top10-1h" or famliy == "throughput":
		continue
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

	sqlstart = 2600
	if (start+3600-start%3600<end):
		a = start+3600-start%3600
		sqltime.append(a)
		while a+3600 < b:
			a = a+ 3600
			sqltime.append(a)
	
	
	cursor.execute("SELECT * FROM DBNS.DRQips WHERE id<")
	testobj = cursor.fetchall()
	print testobj
	obj = "Info:"+str(testobj[0][0])+" name:"+str(testobj[0][1])+" count:"+str(testobj[0][2])
	return render_template('test.html', obj = obj)

if __name__ == '__main__':
	app.run(host='0.0.0.0', port=4321, debug = True)
