hadoop dfs -mkdir /dbns
haddop dfs -mkdir /dbns/sample
hadoop dfs -put ../../../sample/http_response.txt /dbns/sample/http_response.txt
hadoop dfs -put ../../../sample/http_request.txt /dbns/sample/http_request.txt
hadoop dfs -put ../../../sample/dns_response.txt /dbns/sample/dns_response.txt
hadoop dfs -put ../../../sample/dns_request.txt /dbns/sample/dns_request.txt
