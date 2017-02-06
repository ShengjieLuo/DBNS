#used for beeline
#ref to hive.sh to see the hive command!

# connect beeline
!connect jdbc:hive2://127.0.0.1:10000

# Sample result
#Connecting to jdbc:hive2://127.0.0.1:10000
#Enter username for jdbc:hive2://127.0.0.1:10000: root
#Enter password for jdbc:hive2://127.0.0.1:10000: ******
#SLF4J: Class path contains multiple SLF4J bindings.
#SLF4J: Found binding in [jar:file:/usr/local/spark/lib/spark-assembly-1.6.2-hadoop2.7.2.jar!/org/slf4j/impl/StaticLoggerBinder.class]
#SLF4J: Found binding in [jar:file:/usr/local/hadoop/share/hadoop/common/lib/slf4j-log4j12-1.7.10.jar!/org/slf4j/impl/StaticLoggerBinder.class]
#SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
#SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
#2017-01-09 05:16:17,016 INFO  [main] jdbc.Utils: Supplied authorities: 127.0.0.1:10000
#2017-01-09 05:16:17,016 INFO  [main] jdbc.Utils: Resolved authority: 127.0.0.1:10000
#2017-01-09 05:16:17,114 INFO  [main] jdbc.HiveConnection: Will try to open client transport with JDBC Uri: jdbc:hive2://127.0.0.1:10000
#Connected to: Apache Hive (version 1.2.1)
#Driver: Hive JDBC (version 1.2.1)
#Transaction isolation: TRANSACTION_REPEATABLE_READ
#0: jdbc:hive2://127.0.0.1:10000>


