import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapred.JobConf
import org.apache.spark._
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.rdd.RDD
import java.util.Calendar
import org.apache.log4j.Logger
import java.util.Properties
import org.apache.spark.sql.{SQLContext, Row}
import org.apache.spark.sql.types._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.hive.HiveContext._
import java.io.File
import java.util.Date
import java.text.SimpleDateFormat

object LogStreamingAdvancedSparkCluster{

def main(args:Array[String]){
  StreamingExamples.setStreamingLogLevels()

  //step0: Receive the information from Kafka
  val sc = new SparkConf().setAppName("LogStreamingAdvancedSparkCluster").setMaster("spark://172.16.0.104:7077")
  val sssc = new SparkContext(sc)
  val ssc = new StreamingContext(sssc,Seconds(60))
  ssc.checkpoint("/dbns/checkpoint")
  val zkQuorum = "172.16.0.104:2182" //Zookeeper服务器地址
  val group = "1"  //topic所在的group，可以设置为自己想要的名称，比如不用1，而是val group = "test-consumer-group"
  val Array(threA,threB) = args
  val thre1 = threA.toInt

  val topic1 = "httpRequest"   //topics的名
  val topic2 = "httpResponse"
  val topic3 = "dnsRequest"
  val topic4 = "dnsResponse"
  val topic5 = "natlog"
  val topic6 = "syslog"
  val topic7 = "netflow"
  val numThreads = 1  //每个topic的分区数

  val topicMap1 = topic1.split(",").map((_,numThreads.toInt)).toMap
  val topicMap2 = topic2.split(",").map((_,numThreads.toInt)).toMap
  val topicMap3 = topic3.split(",").map((_,numThreads.toInt)).toMap
  val topicMap4 = topic4.split(",").map((_,numThreads.toInt)).toMap  
  val topicMap5 = topic5.split(",").map((_,numThreads.toInt)).toMap  
  val topicMap6 = topic6.split(",").map((_,numThreads.toInt)).toMap
  val topicMap7 = topic7.split(",").map((_,numThreads.toInt)).toMap

  val lineMap1 = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap1)
  val lineMap2 = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap2)
  val lineMap3 = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap3)
  val lineMap4 = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap4)
  val lineMap5 = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap5)
  val lineMap6 = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap6)
  val lineMap7 = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap7)


  //step1: Save the original information into the file system
  //Use the shell to verify result: (shell) cat other/HDFSsample.txt
  //Output is stored in the local filesystem now
  lineMap1.saveAsTextFiles("/dbns/backup/hrq.txt")
  lineMap2.saveAsTextFiles("/dbns/backup/hrs.txt")
  lineMap3.saveAsTextFiles("/dbns/backup/drq.txt")
  lineMap4.saveAsTextFiles("/dbns/backup/drs.txt")
  lineMap5.saveAsTextFiles("/dbns/backup/nat.txt")
  lineMap6.saveAsTextFiles("/dbns/backup/sys.txt")
  lineMap7.saveAsTextFiles("/dbns/backup/net.txt")

  //Use Regular Expression to resolve the natlog and syslog
  val natlogPattern = ("<(\\d+)>([A-Za-z]{3})  (\\d{1,2}) "+"(\\d\\d:\\d\\d:\\d\\d) .*?NAT: ((?:\\d{1,3}\\.){3}\\d{1,3}):"+"(\\d{1,5})->((?:\\d{1,3}\\.){3}\\d{1,3}):(\\d{1,5})\\((\\w+)\\),"+" ([sd]nat) to ((?:\\d{1,3}\\.){3}\\d{1,3}):(\\d{1,5}), .*").r
  val syslogPattern = ("<(\\d+)>([A-Za-z]{3})  (\\d{1,2}) (\\d{4}) "+"(\\d\\d:\\d\\d:\\d\\d) ([A-Za-z\\d\\-]+) (?:%%\\d+)?(\\w+)/(\\d)/" +"([A-Z]+(?:\\([a-z]\\))?)(?:\\[\\d+\\])?:(?:[A-Za-z\\d=,]+)?;?(.*)").r  
  

  //step3: Write the original information into the Hive within SparkSQL
  val lines1 = lineMap1.map(_._2).map(_.split("\t")).filter(_.length>=7)
  val lines2 = lineMap2.map(_._2).map(_.split("\t")).filter(_.length>=7)
  val lines3 = lineMap3.map(_._2).map(_.split("\t")).filter(_.length>=6)
  val lines4 = lineMap4.map(_._2).map(_.split("\t")).filter(_.length>=8)
  val lines5 = lineMap5.map(_._2).map(p => {val natlogPattern(number, month, day, time, src_ip, src_port, dst_ip, dst_port, protocol, nat_type, nat_ip, nat_port) = p;Array(number,month,day,time,src_ip, src_port, dst_ip, dst_port, protocol, nat_type, nat_ip, nat_port);}).filter(_.length>=2) 
  val lines6 = lineMap6.map(_._2).map(p => {val syslogPattern(number, month, day, year, time, hostname, module, severity, program, message) = p;Array(number, month, day, year, time, hostname, module, severity, program, message)}).filter(_.length>=2)
  val lines7 = lineMap4.map(_._2).map(_.split("\t")).filter(_.length>=8)
  

  val hiveCtx = new HiveContext(sssc)
  val schema1 = StructType(List(StructField("time", StringType, true),StructField("TTL", StringType, true),StructField("ips", StringType, true),StructField("ps", StringType, true),StructField("ipd", StringType, true),StructField("pd", StringType, true),StructField("type", StringType, true)))
  lines1.foreachRDD(rdd =>
  {
    val rowrdd = hiveCtx.createDataFrame(rdd.map(p => Row(p(0).trim, p(1).trim, p(2).trim, p(3).trim,p(4).trim,p(5).trim,p(6).trim)), schema1)
    //rowrdd.map(p=>println(p))
    rowrdd.registerTempTable("tempTable")
    hiveCtx.sql("insert into HRQ.original select * from tempTable")
  })

  val schema2 = StructType(List(StructField("time", StringType, true),StructField("TTL", StringType, true),StructField("ips", StringType, true),StructField("ps", StringType, true),StructField("ipd", StringType, true),StructField("pd", StringType, true),StructField("rc", StringType, true)))
  lines2.foreachRDD(rdd =>
  {
    val rowrdd = hiveCtx.createDataFrame(rdd.map(p => Row(p(0).trim, p(1).trim, p(2).trim, p(3).trim,p(4).trim,p(5).trim,p(6).trim)), schema2)
    //rowrdd.map(p=>println(p))
    rowrdd.registerTempTable("tempTable")
    hiveCtx.sql("insert into HRS.original select * from tempTable")
  })

  val schema3 = StructType(List(StructField("time", StringType, true),StructField("ips", StringType, true),StructField("ipd", StringType, true),StructField("name", StringType, true),StructField("type", StringType, true),StructField("class", StringType, true)))
  lines3.foreachRDD(rdd =>
  {
    val rowrdd = hiveCtx.createDataFrame(rdd.map(p => Row(p(0).trim, p(1).trim, p(2).trim, p(3).trim,p(4).trim,p(5).trim)), schema3)
    //rowrdd.map(p=>println(p))
    rowrdd.registerTempTable("tempTable")
    hiveCtx.sql("insert into DRQ.original select * from tempTable")
  })

  val schema4 = StructType(List(StructField("time", StringType, true),StructField("ips", StringType, true),StructField("ipd", StringType, true),StructField("name", StringType, true),StructField("type", StringType, true),StructField("class", StringType, true),StructField("TTL", StringType, true),StructField("url", StringType, true)))
  lines4.foreachRDD(rdd =>
  {
    val rowrdd = hiveCtx.createDataFrame(rdd.map(p => Row(p(0).trim, p(1).trim, p(2).trim, p(3).trim,p(4).trim,p(5).trim,p(6).trim,p(7).trim)), schema4)
    //rowrdd.map(p=>println(p))
    rowrdd.registerTempTable("tempTable")
    hiveCtx.sql("insert into DRS.original select * from tempTable")
  })

  val schema5 = StructType(List(StructField("number", IntegerType, true),StructField("month", StringType, true),StructField("day", StringType, true),StructField("time", StringType, true),StructField("src_ip", StringType, true),StructField("src_port", StringType, true),StructField("dst_ip", StringType, true),StructField("dst_port", StringType, true),StructField("protocol", StringType, true),StructField("nat_type", StringType, true),StructField("nat_ip", StringType, true),StructField("nat_port", StringType, true)))
  lines5.foreachRDD(rdd =>
  {
    val rowrdd = hiveCtx.createDataFrame(rdd.map(p => Row(p(0).trim.toInt, p(1).trim, p(2).trim, p(3).trim,p(4).trim,p(5).trim,p(6).trim,p(7).trim,p(8).trim,p(9).trim,p(10).trim,p(11).trim)), schema5)
    //rowrdd.map(p=>println(p))
    rowrdd.registerTempTable("tempTable")
    hiveCtx.sql("insert into NAT.original select * from tempTable")
  })

  val schema6 = StructType(List(StructField("number", IntegerType, true),StructField("month", StringType, true),StructField("day", StringType, true),StructField("year", StringType, true),StructField("time", StringType, true),StructField("hostname", StringType, true),StructField("module", StringType, true),StructField("serverity", StringType, true),StructField("program", StringType, true),StructField("message", StringType, true)))
  lines6.foreachRDD(rdd =>
  {
    val rowrdd = hiveCtx.createDataFrame(rdd.map(p => Row(p(0).trim.toInt, p(1).trim, p(2).trim, p(3).trim,p(4).trim,p(5).trim,p(6).trim,p(7).trim,p(8).trim,p(9).trim)), schema6)
    rowrdd.map(p=>println(p))
    rowrdd.registerTempTable("tempTable")
    hiveCtx.sql("insert into SYS.original select * from tempTable")
  })

  
  val schema7 = StructType(List(StructField("time", IntegerType, true),StructField("bytes", StringType, true),StructField("packets", StringType, true),StructField("src_ip", StringType, true),StructField("dst_ip", StringType, true),StructField("src_port", StringType, true),StructField("dst_port", StringType, true),StructField("protocol", StringType, true)))
  lines7.foreachRDD(rdd =>
  {
    val rowrdd = hiveCtx.createDataFrame(rdd.map(p => Row(p(0).trim.toInt, p(1).trim, p(2).trim, p(3).trim,p(4).trim,p(5).trim,p(6).trim,p(7).trim)), schema7)
    //rowrdd.map(p=>println(p))
    rowrdd.registerTempTable("tempTable")
    hiveCtx.sql("insert into NET.original select * from tempTable")
  })

  //Step final: start the spark streaming context
  ssc.start
  ssc.awaitTermination
  }
}
