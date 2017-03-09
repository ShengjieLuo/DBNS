## 数据报文接收字段说明

>**文档作者：景彦超**  	
>**邮箱 13541333146@163.com**

[TOC]

### HTTP请求报文Get类型（TCP协议）

说明：目前在服务器10.255.0.10的eno1网卡10666端口提供数据流量

| 字段名        | 字段说明                     |
| ---------- | ------------------------ |
| Time       | 接收到数据报文的时间（Unix seconds） |
| Size       | 报文的总长度                   |
| Src_ip     | 报文源IP地址                  |
| Src_port   | 报文源端口                    |
| Dst_ip     | 报文目的IP地址                 |
| Dst_port   | 报文目的端口                   |
| G          | 标示是get类型报文               |
| Host       | Http报文请求的host站点地址        |
| Referer    | Http报文请求跳转的当前地址          |
| User_agent | 用户浏览器内核类型                |

 

### HTTP请求报文Post类型（TCP协议）

说明：目前在服务器10.255.0.10的eno1网卡10666端口提供数据流量

| 字段名          | 字段说明                     |
| ------------ | ------------------------ |
| Time         | 接收到数据报文的时间（Unix seconds） |
| Size         | 报文的总长度                   |
| Src_ip       | 报文源IP地址                  |
| Src_port     | 报文源端口                    |
| Dst_ip       | 报文目的IP地址                 |
| Dst_port     | 报文目的端口                   |
| P            | 标示是post类型报文              |
| Host         | Http报文请求的host站点地址        |
| Referer      | Http报文请求跳转的当前地址          |
| User_agent   | 用户浏览器内核类型                |
| Content-type | 请求资源类型                   |

 

### HTTP返回报文（TCP协议）

说明：目前在服务器10.255.0.10的eno1网卡10667端口提供数据流量

| 字段名      | 字段说明                     |
| -------- | ------------------------ |
| Time     | 接收到数据报文的时间（Unix seconds） |
| Size     | 报文的总长度                   |
| Src_ip   | 报文源IP地址                  |
| Src_port | 报文源端口                    |
| Dst_ip   | 报文目的IP地址                 |
| Dst_port | 报文目的端口                   |
| Code     | HTTP状态码                  |
| Type     | 资源类型                     |



### HTTP超时报文（TCP协议）

说明：目前在服务器10.255.0.10的eno1网卡10672端口提供数据流量

| 字段名    | 字段说明     |
| ------ | -------- |
| Src_ip | 报文源IP地址  |
| Dst_ip | 报文目的IP地址 |



### DNS请求报文（UDP协议）

说明：目前在服务器10.255.0.10的eno1网卡10668端口提供数据流量

| 字段名       | 字段说明       |
| --------- | ---------- |
| Time      | 接收到数据报文的时间 |
| Client_ip | 客户端IP地址    |
| Server_ip | 服务器IP地址    |
| Qname     | 请求的域名名称    |
| Qtype     | 请求的类型      |
| Qclass    | 请求的域名类型    |



### DNS回复报文（UDP协议）

说明：目前在服务器10.255.0.10的eno1网卡10669端口提供数据流量

| 字段名       | 字段说明       |
| --------- | ---------- |
| Time      | 接收到数据报文的时间 |
| Client_ip | 客户端的IP地址   |
| Server_ip | 服务器的IP地址   |
| Rname     | 请求的域名名称    |
| Rtype     | 请求的类型      |
| Rclass    | 请求的域名类型    |
| TTL       | dns缓存时间    |
| URL       | 解析的URL地址   |



### NETFLOW（UDP）

说明：目前在服务器10.255.0.10的eno1网卡10670端口提供数据流量

| 字段名      | 字段说明          |
| -------- | ------------- |
| IN_BYTES | 报文包含的字节数      |
| IN_PTKS  | 报文包含的packet个数 |
| SRC_IP   | 源ip地址         |
| DST_IP   | 目的ip地址        |
| SRC_PORT | 源端口地址         |
| DST_PORT | 目的端口地址        |
| PROTOCOL | IP协议字段        |



可选字段

| 字段名            | 字段说明                                     |
| -------------- | ---------------------------------------- |
| INPUT_SNMP     | Input interface index                    |
| OUTPUT_SNMP    | Output interface index                   |
| LAST_SWITCHED  | System uptime at which the last packet of this flow was switched |
| FIRST_SWITCHED | System uptime at which the first packet of this flow was switched |
| SRC_AS         |                                          |
| DST_AS         |                                          |
| NEXT_HOP       | IPv4 address of next-hop router          |
| SRC_MASK       |                                          |
| DST_MASK       |                                          |
| TCP_FLAGS      |                                          |
| SRC_TOS        |                                          |
| DIRECTION      |                                          |



### NETSTREAM(UDP协议)

说明：目前在服务器10.255.0.10的eno1网卡10671端口提供数据流量

| 字段名      | 字段说明          |
| -------- | ------------- |
| IN_BYTES | 报文包含的字节数      |
| IN_PTKS  | 报文包含的packet个数 |
| SRC_IP   | 源ip地址         |
| DST_IP   | 目的ip地址        |
| SRC_PORT | 源端口地址         |
| DST_PORT | 目的端口地址        |
| PROTOCOL | IP协议字段        |



可选字段

| 字段名            | 字段说明                            |
| -------------- | ------------------------------- |
| NEXT_HOP       | IPv4 address of next-hop router |
| FIRST_SWITCHED |                                 |
| LAST_SWITCHED  |                                 |
| NEXT_HOP_BGP   |                                 |
| INPUT_SNMP     |                                 |
| OUTPUT_SNMP    |                                 |
| SRC_AS         |                                 |
| DST_AS         |                                 |
| TCP_FLAGS      |                                 |
| SRC_TOS        |                                 |
| SRC_MASK       |                                 |
| DST_MASK       |                                 |
| DIRECTION      |                                 |



netflow netstream url：http://www.cisco.com/en/US/technologies/tk648/tk362/technologies_white_paper09186a00800a3db9.pdf
