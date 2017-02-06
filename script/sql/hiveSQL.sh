create database hive;
grant all on *.* to hive@localhost identified by 'hive';
flush privileges;
update user set host = '%' where user = 'hive';
flush privileges;
