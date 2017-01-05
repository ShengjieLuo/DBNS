create database web;
use web;
create table onHRSips (id varchar(20),IPSource varchar(20),count int);
create table onHRSipd (id varchar(20),IPDest varchar(20),count int);
create table onHRSps (id varchar(20),PortSource varchar(20),count int);
create table onHRSpd (id varchar(20),PortDest varchar(20),count int);
create table onHRSrc (id varchar(20),returnCode varchar(20),count int);

create table onHRQips (id varchar(20), IPSource varchar(20),count int);
create table onHRQipd (id varchar(20),IPDest varchar(20),count int);
create table onHRQps (id varchar(20),PortSource varchar(20),count int);
create table onHRQpd (id varchar(20),PortDest varchar(20),count int);

create table onDRSips(id varchar(20),IPSource varchar(20),count int);
create table onDRSipd(id varchar(20),IPDest varchar(20),count int);
create table onDRSname(id varchar(20),name varchar(100),count int);
create table onDRStype(id varchar(20),type varchar(20),count int);
create table onDRSurl(id varchar(20),url varchar(100),count int);

create table onDRQips(id varchar(20),IPSource varchar(20),count int);
create table onDRQipd(id varchar(20),IPDest varchar(20),count int);
create table onDRQname(id varchar(20),name varchar(100),count int);
create table onDRQtype(id varchar(20),type varchar(20),count int);

create table ofHRSips (id varchar(20),IPSource varchar(20),count int);
create table ofHRSipd (id varchar(20),IPDest varchar(20),count int);
create table ofHRSps (id varchar(20),PortSource varchar(20),count int);
create table ofHRSpd (id varchar(20),PortDest varchar(20),count int);
create table ofHRSrc (id varchar(20),returnCode varchar(20),count int);

create table ofHRQips (id varchar(20), IPSource varchar(20),count int);
create table ofHRQipd (id varchar(20),IPDest varchar(20),count int);
create table ofHRQps (id varchar(20),PortSource varchar(20),count int);
create table ofHRQpd (id varchar(20),PortDest varchar(20),count int);

create table ofDRSips(id varchar(20),IPSource varchar(20),count int);
create table ofDRSipd(id varchar(20),IPDest varchar(20),count int);
create table ofDRSname(id varchar(20),name varchar(100),count int);
create table ofDRStype(id varchar(20),type varchar(20),count int);
create table ofDRSurl(id varchar(20),url varchar(100),count int);

create table ofDRQips(id varchar(20),IPSource varchar(20),count int);
create table ofDRQipd(id varchar(20),IPDest varchar(20),count int);
create table ofDRQname(id varchar(20),name varchar(100),count int);
create table ofDRQtype(id varchar(20),type varchar(20),count int);

