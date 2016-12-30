# coding:utf-8
# 用于对源数据进行处理

sourcefile = open("/root/text/http_request.txt")
targetfile = open("/usr/local/spark/mycode/DBNS/sample/http_request.txt",'w')
lines = sourcefile.readlines()
count = 0
for line in lines:
	targetfile.write(line)
	count += 1
	if count % 1000 == 0:
		print str(count)+": line"
	if count >= 1000000:
		break
	


