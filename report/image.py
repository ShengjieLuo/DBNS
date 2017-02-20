from tools import *
import os
import pygal
from pygal.style import LightStyle

def draw_image(cmd,output,title,c1,num):
	data = stat_data(cmd)
	stat_image(data,title,output,c1,num)
	return "![](file://"+os.environ.get('DBNS_HOME')+"/report/image/"+output+")"

def stat_data(cmd):
	return exeSQLquery(cmd)

def stat_image(data,title,output,c1,num):
	time,key,value,order = [],[],[],[]
	count = 0
	#print data
	for item in data:
		time.append(item[0].encode('utf-8'))
		key.append(item[1].encode('utf-8'))
		value.append(item[2])
		count += 1
		order.append(str(count))
		if count>=num:
			break
	dot_chart = pygal.Dot(x_label_rotation=30,style=LightStyle,height=100)
	dot_chart.title = title
	dot_chart.x_labels = key
	dot_chart.add(c1, value)
	dot_chart.render_to_png(os.environ.get('DBNS_HOME')+"/report/image/"+output)

if __name__=="__main__":
	draw_image("select * from DBNS.DRSipd","DRS IP destination rank","test.png","count",10)
