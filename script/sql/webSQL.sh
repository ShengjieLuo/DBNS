create database web;
use web;
# 在线. HTTP回复.源服务器IP地址
create table onHRSips (id varchar(20),IPSource varchar(20),count int);
# 在线. HTTP回复.目的服务器IP地址
create table onHRSipd (id varchar(20),IPDest varchar(20),count int);
# 在线. HTTP回复.源服务器端口号
create table onHRSps (id varchar(20),PortSource varchar(20),count int);
# 在线. HTTP回复.目的服务器端口号
create table onHRSpd (id varchar(20),PortDest varchar(20),count int);
# 在线. HTTP回复.返回值
create table onHRSrc (id varchar(20),returnCode varchar(20),count int);

# 在线.HTTP请求.源服务器IP地址
create table onHRQips (id varchar(20), IPSource varchar(20),count int);
# 在线. HTTP请求.目的服务器IP地址
create table onHRQipd (id varchar(20),IPDest varchar(20),count int);
# 在线. HTTP请求.源服务器端口号
create table onHRQps (id varchar(20),PortSource varchar(20),count int);
# 在线. HTTP请求.目的服务器端口号
create table onHRQpd (id varchar(20),PortDest varchar(20),count int);

# 在线. DNS回复.源服务器IP地址
create table onDRSips(id varchar(20),IPSource varchar(20),count int);
# 在线. DNS回复.目的服务器IP地址
create table onDRSipd(id varchar(20),IPDest varchar(20),count int);
# 在线. DNS回复.域名名称（注意长度为100）
create table onDRSname(id varchar(20),name varchar(100),count int);
# 在线. DNS回复.请求类型
create table onDRStype(id varchar(20),type varchar(20),count int);
# 在线. DNS回复.解析的url地址（注意长度为100）
create table onDRSurl(id varchar(20),url varchar(100),count int);

# 在线. DNS请求.源服务器IP地址
create table onDRQips(id varchar(20),IPSource varchar(20),count int);
# 在线. DNS请求.目的服务器IP地址
create table onDRQipd(id varchar(20),IPDest varchar(20),count int);
# 在线. DNS请求.域名名称（注意长度为100）
create table onDRQname(id varchar(20),name varchar(100),count int);
# 在线. DNS请求.请求类型
create table onDRQtype(id varchar(20),type varchar(20),count int);

# 以下为离线部分，除在线/离线的区分外，其他内容同上
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

