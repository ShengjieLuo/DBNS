#DBNS Offline Report

## Report Overview

###DBNS Overview
Data-Based Network Security system, (aka DBNS)  is a network security system composed of latest opensource big data components. Within the architecture building and efficiency optimization, the DBNS system can afford various methods to analysis 1GBPS network flow within a single node server, and up to 10GBPS network flow within a three-node server cluster.

DBNS Home:[https://github.com/ShengjieLuo/DBNS/](https://github.com/ShengjieLuo/DBNS/)

DBNS Document:[http://dbnsdoc.readthedocs.io/en/latest](http://dbnsdoc.readthedocs.io/en/latest)

DBNS system is developed by Network Computing Center of Department of Computer Science and Engineering, Shanghai Jiao Tong University. You can contact luoshengjie@sjtu.edu.cn for more information.

###Offline Report Overview
The offline report is built for user of DBNS and established each day. The report is composed of two parts, monitor part and probe part. The monitor serverd as netflow monitor, and the probe serverd as hazard detection.
The report information:

Build Time: 2017-02-20 23:35

Component: Monitor & Probe

DBNS Version: 0.2.0

DBNS Framework: spark-based

DBNS Master: 172.16.0.104

DBNS Slaves: 172.16.0.59 172.16.0.68 

DBNS Component:

DBNS Message Forwarding Component: kafka

DBNS Streaming Computation Component: spark-streaming

DBNS Online Computation Component: Python on mySQL

DBNS Offline Computation Component: Hive on Spark

DBNS Metadata Store Component: mysql::DBNS::metadata

DBNS Tempdata Store Component: mysql::web

DBNS Basic Data Store Component: HDFS

DBNS Distribution:

Streaming Task Cores: 48

Online Analysis Cores: 1

Offline Analysis Cores: 10


## Monitor Report

Monitor Function is used to monitor the network flow from the backbone router. In this report, we would include four message resources,

- **DRQ**: DNS request package

- **DRS**: DNS response package

- **HRQ**: HTTP request package

- **HRS**: HTTP response package

### DRQ report
#### Overall situation:
DNS request report is the package sent to the DNS server to query the IP address of the URL.

Total Number of the DRQ package:{Monitor::DRQ::number}

#### Who send the DRQ package?
{Monitor::DRQ::table1}
{Monitor::DRQ::table2}
{Monitor::DRQ::image1}
{Monitor::DRQ::image2}

#### Who receive the DRQ package?
{Monitor::DRQ::table3}
{Monitor::DRQ::table4}
{Monitor::DRQ::image3}
{Monitor::DRQ::image4}

### DRS report
#### Overall situation:
DNS response report is the package reponsed by the DNS server. DNS server would inform the IP address to the server who sent the DNS query request.

Total Number of the DRS package:{Monitor::DRS::number}

#### Who send the DRS package?
<table><thead><tr><th></th><th>First Occurred Time</th><th>IPaddress</th><th>Frequency</th></tr></thead><tbody><tr><td>1</td><td>1480868217</td><td>202.121.64.5</td><td>79656</td></tr><tr><td>2</td><td>1480868226</td><td>192.168.2.116</td><td>55667</td></tr><tr><td>3</td><td>1480867249</td><td>210.35.96.6</td><td>48998</td></tr><tr><td>4</td><td>1480868217</td><td>202.121.209.11</td><td>46905</td></tr><tr><td>5</td><td>1480868217</td><td>210.35.96.2</td><td>39358</td></tr><tr><td>6</td><td>1480867249</td><td>202.121.64.7</td><td>33154</td></tr><tr><td>7</td><td>1480868218</td><td>192.168.2.215</td><td>18028</td></tr><tr><td>8</td><td>1480867249</td><td>202.121.64.130</td><td>17444</td></tr><tr><td>9</td><td>1480868221</td><td>192.168.2.223</td><td>7519</td></tr><tr><td>10</td><td>1480868055</td><td>202.121.223.29</td><td>5855</td></tr></tbody></table>
{Monitor::DRS::DRSps::table}
![]file:///usr/local/DBNS/report/image/Monitor::DRS::DRSips::image.png
{Monitor::DRS::DRSps::image}

#### Who receive the DRS package?
<table><thead><tr><th></th><th>First Occurred Time</th><th>IPaddress</th><th>Frequency</th></tr></thead><tbody><tr><td>1</td><td>1480867249</td><td>202.121.64.5</td><td>87862</td></tr><tr><td>2</td><td>1480867249</td><td>202.120.2.101</td><td>84712</td></tr><tr><td>3</td><td>1480868217</td><td>101.7.8.9</td><td>52793</td></tr><tr><td>4</td><td>1480867249</td><td>210.22.84.3</td><td>32207</td></tr><tr><td>5</td><td>1480867249</td><td>8.8.8.8</td><td>30123</td></tr><tr><td>6</td><td>1480867249</td><td>210.22.70.3</td><td>24833</td></tr><tr><td>7</td><td>1480868218</td><td>210.35.96.6</td><td>7357</td></tr><tr><td>8</td><td>1480868217</td><td>210.35.96.2</td><td>3454</td></tr><tr><td>9</td><td>1480868218</td><td>114.114.114.114</td><td>2188</td></tr><tr><td>10</td><td>1480868285</td><td>202.121.209.11</td><td>2149</td></tr></tbody></table>
{Monitor::DRS::DRSpd::table}
![]file:///usr/local/DBNS/report/image/Monitor::DRS::DRSips::image.png
{Monitor::DRS::DRSps::image}

### HRQ report
#### Overall situation:
Http request package is one of the most popular package type in Network. For example, if you want to get a net page from www.baidu.com, you would send a HTTP request to the baidu server to fetch the net page.

Total Number of the DRQ package:{Monitor::HRQ::number}
#### Who send the HRQ package?
{Monitor::HRQ::table1}
{Monitor::HRQ::table2}
{Monitor::HRQ::image1}
{Monitor::DRS::image2}

#### Who receive the HRQ package?
{Monitor::DRS::table3}
{Monitor::DRS::table4}
{Monitor::DRS::image3}
{Monitor::DRS::image4}

### HRS report
#### Overall situation:
HTTP response package is one of the most popular package type in Internet. For example, if you want to get a net page from www.baidu.com, then baidu server would send you a HTTP response package including the netpage you want.

Total Number of the DRQ package:{Monitor::HRS:number}

#### Who send the HRQ package?
{Monitor::HRQ::table1}
{Monitor::HRQ::table2}
{Monitor::HRQ::image1}
{Monitor::DRS::image2}

#### Who receive the HRQ package?
{Monitor::DRS::table3}
{Monitor::DRS::table4}
{Monitor::DRS::image3}
{Monitor::DRS::image4}
Probe Report 
===================================== 
Conclusion of Intellegent Maintain System 
=====================================
