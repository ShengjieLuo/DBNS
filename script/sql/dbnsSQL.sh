use DBNS;

create table offstatus(analysisTime int, item varchar(5), firsttime int, lasttime int);
insert into offstatus values (0,"DRQ",0,0);
insert into offstatus values (0,"HRQ",0,0);
insert into offstatus values (0,"HRS",0,0);
insert into offstatus values (0,"DRS",0,0);

create table latest(time int);
insert into latest values (0);


create table onstatus(analysisTime int, firsttime int, lasttime int);


create table HRSips (id varchar(20),IPSource varchar(20),count int);
create table HRSipd (id varchar(20),IPDest varchar(20),count int);
create table HRSps (id varchar(20),PortSource varchar(20),count int);
create table HRSpd (id varchar(20),PortDest varchar(20),count int);
create table HRSrc (id varchar(20),returnCode varchar(20),count int);

create table HRQips (id varchar(20), IPSource varchar(20),count int);
create table HRQipd (id varchar(20),IPDest varchar(20),count int);
create table HRQps (id varchar(20),PortSource varchar(20),count int);
create table HRQpd (id varchar(20),PortDest varchar(20),count int);

create table DRSips(id varchar(20),IPSource varchar(20),count int);
create table DRSipd(id varchar(20),IPDest varchar(20),count int);
create table DRSname(id varchar(20),name varchar(100),count int);
create table DRStype(id varchar(20),type varchar(20),count int);
create table DRSurl(id varchar(20),url varchar(100),count int);

create table DRQips(id varchar(20),IPSource varchar(20),count int);
create table DRQipd(id varchar(20),IPDest varchar(20),count int);
create table DRQname(id varchar(20),name varchar(100),count int);
create table DRQtype(id varchar(20),type varchar(20),count int);

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
