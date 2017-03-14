import httplib

def test_item(url):
	conn = httplib.HTTPConnection("spark-master:4321")
	conn.request(method="GET",url=url) 
	response = conn.getresponse()
	res= response.read()
	print res

def main():
	test_item("http://spark-master:4321/http_request/top10-1h/1489060000/1489064800")
	test_item("http://spark-master:4321/http_response/top10-1h/1489060000/1489064800")
	test_item("http://spark-master:4321/dns_request/top10-1h/1489060000/1489064800")
	test_item("http://spark-master:4321/dns_response/top10-1h/1489060000/1489064800")
	test_item("http://spark-master:4321/http_request/throughput/1489060000/1489064800")
	test_item("http://spark-master:4321/http_response/throughput/1489060000/1489064800")
	test_item("http://spark-master:4321/dns_request/throughput/1489060000/1489064800")
	test_item("http://spark-master:4321/dns_response/throughput/1489060000/1489064800")

if __name__=="__main__":
	main()
