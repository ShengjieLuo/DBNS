create database stat;
use stat;
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

