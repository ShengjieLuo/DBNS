DROP TABLE DBNStest;
CREATE TABLE DBNStest;
mysqldump DBNS -u root -p --opt | mysql DBNStest -u root -p
