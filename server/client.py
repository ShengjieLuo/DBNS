import httplib

url = "http://172.16.0.104:4321/"
conn = httplib.HTTPConnection("172.16.0.104:4321")

#url = "http://www.baidu.com"
#conn = httplib.HTTPConnection("www.baidu.com")

conn.request(method="GET",url=url) 
response = conn.getresponse()
res= response.read()
print res
