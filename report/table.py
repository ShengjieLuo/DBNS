from tools import *
import pygal

def draw_table(cmd,output,title,c1,c2,c3,num):
	data = stat_data(cmd)
	return stat_table(data,output,title,c1,c2,c3,num)

def stat_data(cmd):
	return exeSQLquery(cmd)

def stat_table(data,output,title,c1,c2,c3,num):
	number = len(data)
	line_chart = pygal.Bar()
	line_chart.title = title
	line_chart.x_labels = map(str, range(1, min(num,number)+1))
	time,timebak,key,keybak,value = [],[],[],[],[]
	count, count1, count2 = 0,65536,0
	for item in data:
		count += 1
		count1 += 1
		count2 -= 1
		time.append(item[0].encode('utf-8'))
		key.append(item[1].encode('utf-8'))
		value.append(item[2])
		timebak.append(count1)
		keybak.append(count2)
		if count>=num:
			break
	line_chart.add(c1, timebak)
	line_chart.add(c2, keybak)
	line_chart.add(c3, value)
	#line_chart.value_formatter = lambda x: '%.2f%%' % x if x is not None else '0'
	#line_chart.render_to_png("test.png")
	mdtable = line_chart.render_table()
	htmltable = line_chart.render_table(style=True)
	for i in range(65537,count1+1):
		mdtable = sub2(mdtable,"<td>"+str(i)+"</td>","<td>"+time[i-65537]+"</td>")
		htmltable = sub2(htmltable,"<td>"+str(i)+"</td>","<td>"+time[i-65537]+"</td>")
	for i in range(-1,count2-1,-1):
		mdtable = sub2(mdtable,"<td>"+str(i)+"</td>","<td>"+key[-1*(i+1)]+"</td>")
		htmltable = sub2(htmltable,"<td>"+str(i)+"</td>","<td>"+key[-1*(i+1)]+"</td>")
	#print htmltable
	return [mdtable,htmltable]

if __name__ == "__main__":
	data = stat_data_test()
	#print data
	stat_table(data,"test.png","test","First Occurred Time","IPaddress","Frequency",10)
