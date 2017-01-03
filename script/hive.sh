create database if not exists HRS;
create database if not exists HRQ;
create database if not exists DRS;
create database if not exists DRQ;
create table if not exists HRS.original(time string, TTL string, ips string, ps string, ipd string, pd string, rc string);
create table if not exists HRQ.original(time string, TTL string, ips string, ps string, ipd string, pd string, add1 string, add2 string, add3 string, add4 string);
create table if not exists DRQ.original(time string, ips string, ipd string, url string, add1 string, add2 string);
create table if not exists DRS.original(time string, ips string, ipd string, dns string, add1 string, add2 string, add3 string, ip string);
