use DBNS;

create table offstatus(analysisTime int, item varchar(5), firsttime int, lasttime int);
insert into offstatus values (0,"DRQ",0,0);
insert into offstatus values (0,"HRQ",0,0);
insert into offstatus values (0,"HRS",0,0);
insert into offstatus values (0,"DRS",0,0);

create table latest(time int);
insert into latest values (0);


create table onstatus(analysisTime int, firsttime int, lasttime int);


create table HRSips (time int,IPSource varchar(20),count int);
create table HRSipd (time int,IPDest varchar(20),count int);
create table HRSps (time int,PortSource varchar(20),count int);
create table HRSpd (time int,PortDest varchar(20),count int);
create table HRSrc (time int,returnCode varchar(20),count int);

create table HRQips (time int, IPSource varchar(20),count int);
create table HRQipd (time int,IPDest varchar(20),count int);
create table HRQps (time int,PortSource varchar(20),count int);
create table HRQpd (time int,PortDest varchar(20),count int);

create table DRSips(time int,IPSource varchar(20),count int);
create table DRSipd(time int,IPDest varchar(20),count int);
create table DRSname(time int,name varchar(100),count int);
create table DRStype(time int,type varchar(20),count int);
create table DRSurl(time int,url varchar(100),count int);

create table DRQips(time int,IPSource varchar(20),count int);
create table DRQipd(time int,IPDest varchar(20),count int);
create table DRQname(time int,name varchar(100),count int);
create table DRQtype(time int,type varchar(20),count int);

truncate table HRSips;
truncate table HRSipd;
truncate table HRSps;
truncate table HRSpd;
truncate table HRSrc;
truncate table HRQips;
truncate table HRQipd;
truncate table HRQps;
truncate table HRQpd;
truncate table DRSips;
truncate table DRSipd;
truncate table DRSname;
truncate table DRStype;
truncate table DRSurl;
truncate table DRQips;
truncate table DRQipd;
truncate table DRQname;
truncate table DRQtype;
truncate table offstatus;
truncate table onstatus;
truncate table latest;

DROP table HRSips;
DROP table HRSipd;
DROP table HRSps;
DROP table HRSpd;
DROP table HRSrc;
DROP table HRQips;
DROP table HRQipd;
DROP table HRQps;
DROP table HRQpd;
DROP table DRSips;
DROP table DRSipd;
DROP table DRSname;
DROP table DRStype;
DROP table DRSurl;
DROP table DRQips;
DROP table DRQipd;
DROP table DRQname;
DROP table DRQtype;
DROP table offstatus;
DROP table onstatus;
DROP table latest;

