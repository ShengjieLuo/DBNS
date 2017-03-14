use DBNS;

create table offstatus(analysisTime int, item varchar(5), firsttime int, lasttime int);
create table latest(time int);
create table onstatus(analysisTime int, firsttime int, lasttime int);

insert into offstatus values (0,"DRQ",0,0);
insert into offstatus values (0,"HRQ",0,0);
insert into offstatus values (0,"HRS",0,0);
insert into offstatus values (0,"DRS",0,0);
insert into offstatus values (0,"NET",0,0);
insert into latest values (0);

create table HRSips (time int,IPSource varchar(20),count int);
create table HRSipd (time int,IPDest varchar(20),count int);
create table HRSps (time int,PortSource varchar(20),count int);
create table HRSpd (time int,PortDest varchar(20),count int);
create table HRSrc (time int,returnCode varchar(20),count int);
create table HRSrcs (time int,returnCode varchar(20),size long);
create table HRSipds (time int,IP varchar(20),size long);
create table HRSipss (time int,IP varchar(20),size long);
create table HRSsum (time int, count long);
create table HRScount (time int, count long);

create table HRQips (time int, IPSource varchar(20),count int);
create table HRQipd (time int,IPDest varchar(20),count int);
create table HRQps (time int,PortSource varchar(20),count int);
create table HRQpd (time int,PortDest varchar(20),count int);
create table HRQtype (time int, returnCode varchar(20),count int);
create table HRQtypes (time int, returnCode varchar(20),size long);
create table HRQipds (time int,IP varchar(20),size long);
create table HRQipss (time int,IP varchar(20),size long);
create table HRQsum (time int, count long);
create table HRQcount (time int, count long);

create table DRSips(time int,IPSource varchar(20),count int);
create table DRSipd(time int,IPDest varchar(20),count int);
create table DRSname(time int,name varchar(100),count int);
create table DRStype(time int,type varchar(20),count int);
create table DRSurl(time int,url varchar(100),count int);
create table DRScount (time int, count long);

create table DRQips(time int,IPSource varchar(20),count int);
create table DRQipd(time int,IPDest varchar(20),count int);
create table DRQname(time int,name varchar(100),count int);
create table DRQtype(time int,type varchar(20),count int);
create table DRQcount (time int, count long);

create table NETips (time int, IPSource varchar(20),count int);
create table NETipd (time int,IPDest varchar(20),count int);
create table NETps (time int,PortSource varchar(20),count int);
create table NETpd (time int,PortDest varchar(20),count int);
create table NETtype (time int, returnCode varchar(20),count int);
create table NETtypes (time int, returnCode varchar(20),size long);
create table NETipds (time int,IP varchar(20),size long);
create table NETipss (time int,IP varchar(20),size long);
create table NETsum (time int, count long);
create table NETcount (time int, count long);

truncate table HRSips;
truncate table HRSipd;
truncate table HRSps;
truncate table HRSpd;
truncate table HRSrc;
truncate table HRSrcs;
truncate table HRSipss;
truncate table HRSipds;
truncate table HRSsum;
truncate table HRScount;

truncate table HRQips;
truncate table HRQipd;
truncate table HRQps;
truncate table HRQpd;
truncate table HRQtype;
truncate table HRQtypes;
truncate table HRQipss;
truncate table HRQipds;
truncate table HRQsum;
truncate table HRQcount;

truncate table DRSips;
truncate table DRSipd;
truncate table DRSname;
truncate table DRStype;
truncate table DRSurl;
truncate table DRScount;

truncate table DRQips;
truncate table DRQipd;
truncate table DRQname;
truncate table DRQtype;
truncate table DRQcount;

truncate table NETips;
truncate table NETipd;
truncate table NETtype;
truncate table NETtypes;
truncate table NETipss;
truncate table NETipds;
truncate table NETsum;
truncate table NETcount;

truncate table offstatus;
truncate table onstatus;
truncate table latest;

DROP table HRSips;
DROP table HRSipd;
DROP table HRSps;
DROP table HRSpd;
DROP table HRSrc;
DROP table HRSrcs;
DROP table HRSipss;
DROP table HRSipds;
DROP table HRSsum;
DROP table HRScount;
DROP table HRQips;
DROP table HRQipd;
DROP table HRQps;
DROP table HRQpd;
DROP table HRQtype;
DROP table HRQtypes;
DROP table HRQipss;
DROP table HRQipds;
DROP table HRQsum;
DROP table HRQcount;
DROP table DRSips;
DROP table DRSipd;
DROP table DRSname;
DROP table DRStype;
DROP table DRSurl;
DROP table DRScount;
DROP table DRQips;
DROP table DRQipd;
DROP table DRQname;
DROP table DRQtype;
DROP table DRQcount;

DROP table offstatus;
DROP table onstatus;
DROP table latest;

