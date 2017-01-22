DROP DATABASE IF EXISTS HRS CASCADE;
DROP DATABASE IF EXISTS HRQ CASCADE;
DROP DATABASE IF EXISTS DRS CASCADE;
DROP DATABASE IF EXISTS DRQ CASCADE;


create database if not exists HRS;
create database if not exists HRQ;
create database if not exists DRS;
create database if not exists DRQ;
create table if not exists HRS.original(time string, TTL string, ips string, ps string, ipd string, pd string, rc string);
create table if not exists HRQ.original(time string, TTL string, ips string, ps string, ipd string, pd string, type string);
create table if not exists DRQ.original(time string, ips string, ipd string, name string, type string, class string);
create table if not exists DRS.original(time string, ips string, ipd string, name string, type string, class string, TTL string, url string);
