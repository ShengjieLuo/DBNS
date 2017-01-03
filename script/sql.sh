create database stat;
use stat;
create table HRSips (IPSource varchar(20),count int);
create table HRSipd (IPDest varchar(20),count int);
create table HRSps (PortSource varchar(20),count int);
create table HRSpd (PortDest varchar(20),count int);
create table HRSrc (returnCode varchar(20),count int);

create table HRQips ( IPSource varchar(20),count int);
create table HRQipd (IPDest varchar(20),count int);
create table HRQps (PortSource varchar(20),count int);
create table HRQpd (PortDest varchar(20),count int);

create table DRSips(IPSource varchar(20),count int);
create table DRSipd(IPDest varchar(20),count int);
create table DRSname(name varchar(100),count int);
create table DRStype(type varchar(20),count int);
create table DRSurl(url varchar(100),count int);


create database hive;
grant all on *.* to hive@localhost identified by 'hive';
flush privileges;

