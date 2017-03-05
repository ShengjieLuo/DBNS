#coding=utf-8
from flask import Flask, request, render_template
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

@app.route('/')
def showbookname():
	cursor = mysql.get_db().cursor()
	cursor.execute("SELECT * FROM DBNS.DRQips")
	testobj = cursor.fetchall()
	print testobj
	obj = "Info:"+str(testobj[0][0])+" name:"+str(testobj[0][1])+" count:"+str(testobj[0][2])
	return render_template('test.html', obj = obj)

if __name__ == '__main__':
	app.run(host='0.0.0.0', port=4321, debug = True)
