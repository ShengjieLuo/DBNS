DROP DATABASE IF EXISTS HRS CASCADE;
DROP DATABASE IF EXISTS HRQ CASCADE;
DROP DATABASE IF EXISTS DRS CASCADE;
DROP DATABASE IF EXISTS DRQ CASCADE;
DROP DATABASE IF EXISTS NAT CASCADE;
DROP DATABASE IF EXISTS SYS CASCADE;
DROP DATABASE IF EXISTS NET CASCADE;


create database if not exists HRS;
create database if not exists HRQ;
create database if not exists DRS;
create database if not exists DRQ;
create database if not exists NAT;
create database if not exists SYS;
create database if not exists NET;

create table if not exists HRS.original(time string, TTL string, ips string, ps string, ipd string, pd string, rc string);
create table if not exists HRQ.original(time string, TTL string, ips string, ps string, ipd string, pd string, type string);
create table if not exists DRQ.original(time string, ips string, ipd string, name string, type string, class string);
create table if not exists DRS.original(time string, ips string, ipd string, name string, type string, class string, TTL string, url string);
create table if not exists NAT.original(number string, month string, day string, time string, src_ip string, src_port string, dst_ip string, dst_port string, protocol string, nat_type string, nat_ip string, nat_port string);
create table if not exists SYS.original(number string, month string, day string, year string, time string, hostname string, module string, severity string, program string, message string);
create table if not exists NET.original(time string,bytes string,packets string,src_ip string, dst_ip string, src_port string, dst_port string, protocol string);
