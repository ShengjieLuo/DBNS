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

Build Time: 2017-02-18 17:47

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


Monitor Report 
===================================== 
Probe Report 
===================================== 
Conclusion of Intellegent Maintain System 
=====================================
