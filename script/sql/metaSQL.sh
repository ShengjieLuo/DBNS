create database DBNS;
use DBNS;
create table metadata(name varchar(50),value varchar(50));
INSERT INTO metadata (name,value) VALUES ("version","0.2.0");
INSERT INTO metadata (name,value) VALUES ("frame","spark-based");
INSERT INTO metadata (name,value) VALUES ("master","spark-master");
INSERT INTO metadata (name,value) VALUES ("slaves","172.16.0.59");
INSERT INTO metadata (name,value) VALUES ("slaves","172.16.0.68");
INSERT INTO metadata (name,value) VALUES ("message","kafka");
INSERT INTO metadata (name,value) VALUES ("stream","spark-streaming");
INSERT INTO metadata (name,value) VALUES ("online","Python on mySQL");
INSERT INTO metadata (name,value) VALUES ("offline","Hive on Spark");
INSERT INTO metadata (name,value) VALUES ("meta","mysql::DBNS::metadata");
INSERT INTO metadata (name,value) VALUES ("temp","mysql::web");
INSERT INTO metadata (name,value) VALUES ("basic","HDFS");
INSERT INTO metadata (name,value) VALUES ("stream-cores","48");
INSERT INTO metadata (name,value) VALUES ("online-cores","1");
INSERT INTO metadata (name,value) VALUES ("offline-cores","10");
