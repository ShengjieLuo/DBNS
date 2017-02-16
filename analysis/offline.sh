#Stat: Most frequently occurred items
SELECT ipd,count(*) as count FROM drq.original GROUP BY ipd having count > 100 ORDER BY count DESC LIMIT 50;

#Stat: items numbers
SELECT count(*) FROM drq.original

#Stat: Select specific items(Recommendation in HBase instead of Hive)
SELECT * FROM drq.original WHERE ipd = "8.8.8.8"

#Stat: Select specific items in Fuzzy Query (Recommendation in HBase instead of Hive)
SELECT * FROM drq.original WHERE time like "143***"

#Example: TCP Flood
S
