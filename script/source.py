# coding:utf-8
# 用于对源数据进行处理

def cutshort(source,target):
	sourcefile = open(source)
	targetfile = open(target,'w')
	line = sourcefile.readline()
	count = 0
	while line:
		targetfile.write(line)
		line = sourcefile.readline()
		count += 1
		if count % 1000 == 0:
			print str(count)+": line"
		if count >= 500000:
			break

if __name__ == "__main__":
	cutshort("/usr/local/spark/mycode/DBNS/sample/http_request.txt","/usr/local/spark/mycode/DBNS/sample/http_request2.txt")
	#cutshort("/root/text/http_response.txt","/usr/local/spark/mycode/DBNS/sample/http_response.txt")
	#cutshort("/root/text/dns_request.txt","/usr/local/spark/mycode/DBNS/sample/dns_request.txt")
	#cutshort("/root/text/dns_response.txt","/usr/local/spark/mycode/DBNS/sample/dns_response.txt")


