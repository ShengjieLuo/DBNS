create database stat;
use stat;
create table HRSips (IPSource varchar(20),count int);
create table HRSipd (IPSource varchar(20),count int);
create table HRSps (IPSource varchar(20),count int);
create table HRSpd (IPSource varchar(20),count int);
create table HRSrc (IPSource varchar(20),count int);
create table HRQips (IPSource varchar(20),count int);
create table HRQipd (IPSource varchar(20),count int);
create table HRQps (IPSource varchar(20),count int);
create table HRQpd (IPSource varchar(20),count int);
create database hive;
grant all on *.* to hive@localhost identified by 'hive';
flush privileges;

