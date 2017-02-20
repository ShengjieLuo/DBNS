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

Build Time: 2017-02-20 19:11

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

Total Number of the DRQ package:{Monitor::all:number}

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

#### Who send the DRS package?
{Monitor::DRS::table1}
{Monitor::DRS::table2}
{Monitor::DRS::image1}
{Monitor::DRS::image2}

#### Who receive the DRS package?
{Monitor::DRS::table3}
{Monitor::DRS::table4}
{Monitor::DRS::image3}
{Monitor::DRS::image4}

### HRQ report
#### Overall situation:
Http request package is one of the most popular package type in Network. For example, if you want to get a net page from www.baidu.com, you would send a HTTP request to the baidu server to fetch the net page.

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
