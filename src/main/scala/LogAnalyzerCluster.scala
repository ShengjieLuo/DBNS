//import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapred.JobConf
import org.apache.spark._
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka.KafkaUtils
//import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
//import org.apache.hadoop.hbase.io.ImmutableBytesWritable
//import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Put,Table,Result}
//import org.apache.hadoop.hbase.util.Bytes
//import org.apache.hadoop.hbase.{HTableDescriptor,HColumnDescriptor,HBaseConfiguration,TableName}
import org.apache.spark.rdd.RDD
import java.util.Calendar
//import org.apache.commons.codec.binary.Base64
//import org.apache.http.HttpResponse
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.HttpClient
//import org.apache.http.client.methods.{HttpGet, HttpPut}
//import org.apache.http.entity.StringEntity
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients
import org.apache.log4j.Logger
import java.util.Properties
import org.apache.spark.sql.{SQLContext, Row}
import org.apache.spark.sql.types._
//import org.apache.spark.sql.hive.HiveContext
//import org.apache.spark.sql.hive.HiveContext._
import java.io.File
import java.util.Date
import java.text.SimpleDateFormat
//import hiveContext.implicits._

object LogStreamingAdvancedSparkCluster{

def main(args:Array[int]){
  StreamingExamples.setStreamingLogLevels()

  //step0: Receive the information from Kafka
  val sc = new SparkConf().setAppName("LogStreamingAdvancedSparkCluster").setMaster("spark://172.16.0.104:7077")
  val sssc = new SparkContext(sc)
  val ssc = new StreamingContext(sssc,Seconds(60))
  ssc.checkpoint("/dbns/checkpoint")
  val zkQuorum = "172.16.0.104:2182" //Zookeeper服务器地址
  val group = "1"  //topic所在的group，可以设置为自己想要的名称，比如不用1，而是val group = "test-consumer-group"
  val Array(thre1,thre2) = args

  val topic1 = "httpRequest"   //topics的名
  val topic2 = "httpResponse"
  val topic3 = "dnsRequest"
  val topic4 = "dnsResponse"
  val numThreads = 1  //每个topic的分区数

  val topicMap1 =topic1.split(",").map((_,numThreads.toInt)).toMap
  val topicMap2 =topic2.split(",").map((_,numThreads.toInt)).toMap
  val topicMap3 =topic3.split(",").map((_,numThreads.toInt)).toMap
  val topicMap4 =topic4.split(",").map((_,numThreads.toInt)).toMap
  
  val lineMap1 = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap1)
  val lineMap2 = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap2)
  val lineMap3 = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap3)
  val lineMap4 = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap4)


  //step1: Save the original information into the file system
  //Use the shell to verify result: (shell) cat other/HDFSsample.txt
  //Output is stored in the local filesystem now
  lineMap1.saveAsTextFiles("/dbns/backup/hrq.txt")
  lineMap2.saveAsTextFiles("/dbns/backup/hrs.txt")
  lineMap3.saveAsTextFiles("/dbns/backup/drq.txt")
  lineMap4.saveAsTextFiles("/dbns/backup/drs.txt")

  //step3: Write the original information into the Hive within SparkSQL
  
  val lines1 = lineMap1.map(_._2).map(_.split("\t")).filter(_.length>=7)
  val lines2 = lineMap2.map(_._2).map(_.split("\t")).filter(_.length>=7)
  val lines3 = lineMap3.map(_._2).map(_.split("\t")).filter(_.length>=6)
  val lines4 = lineMap4.map(_._2).map(_.split("\t")).filter(_.length>=8)
  /*
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
*/
  //Step4: Write the statistical data into the SparkSQL --> mySQL
  val sqlContext = new SQLContext(sssc)
  val ipsschema = StructType(List(StructField("id",StringType,true),StructField("IPSource",StringType,true),StructField("count",IntegerType,true)))
  val ipdschema = StructType(List(StructField("id",StringType,true),StructField("IPDest",StringType,true),StructField("count",IntegerType,true)))
  val nameschema  = StructType(List(StructField("id",StringType,true),StructField("name",StringType,true),StructField("count",IntegerType,true)))
  val typeschema  = StructType(List(StructField("id",StringType,true),StructField("type",StringType,true),StructField("count",IntegerType,true)))
  val psschema  = StructType(List(StructField("id",StringType,true),StructField("PortSource",StringType,true),StructField("count",IntegerType,true)))
  val pdschema  = StructType(List(StructField("id",StringType,true),StructField("PortDest",StringType,true),StructField("count",IntegerType,true)))
  val urlschema  = StructType(List(StructField("id",StringType,true),StructField("url",StringType,true),StructField("count",IntegerType,true)))
  val rcschema  = StructType(List(StructField("id",StringType,true),StructField("returnCode",StringType,true),StructField("count",IntegerType,true)))


  val prop = new Properties()
  prop.put("user", "root")
  prop.put("password", "123456")
  prop.put("driver","com.mysql.jdbc.Driver")
  val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm")  

  lines1.foreachRDD(words =>
    {
    // Use Sort + Threshold to Implement
    val id1:String = dateFormat.format(new Date())
    words.map(a => a.map(b => println(b)))
    val IPSourceTop     = words.map(x => (x(2),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id1,p._2.trim,p._1.toInt))
    val PortSourceTop   = words.map(x => (x(3),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id1,p._2.trim,p._1.toInt))
    val IPDestTop       = words.map(x => (x(4),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id1,p._2.trim,p._1.toInt))
    val PortDestTop     = words.map(x => (x(5),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id1,p._2.trim,p._1.toInt))
    sqlContext.createDataFrame(IPSourceTop,ipsschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.HRQips", prop)
    sqlContext.createDataFrame(PortSourceTop,psschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.HRQps", prop)
    sqlContext.createDataFrame(IPDestTop,ipdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.HRQipd", prop)
    sqlContext.createDataFrame(PortDestTop,pdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.HRQpd", prop)
    })

  lines2.foreachRDD(words =>
    {
    // Use Sort + Threshold to Implement
    val id2:String = dateFormat.format(new Date())
    val IPSourceTop     = words.map(x => (x(2),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id2,p._2.trim,p._1.toInt))
    val PortSourceTop   = words.map(x => (x(3),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id2,p._2.trim,p._1.toInt))
    val IPDestTop       = words.map(x => (x(4),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id2,p._2.trim,p._1.toInt))
    val PortDestTop     = words.map(x => (x(5),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id2,p._2.trim,p._1.toInt))
    val rcTop     = words.map(x => (x(6),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>5).map(p => Row(id2,p._2.trim,p._1.toInt))
    sqlContext.createDataFrame(IPSourceTop,ipsschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.HRSips", prop)
    sqlContext.createDataFrame(PortSourceTop,psschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.HRSps", prop)
    sqlContext.createDataFrame(IPDestTop,ipdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.HRSipd", prop)
    sqlContext.createDataFrame(PortDestTop,pdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.HRSpd", prop)
    sqlContext.createDataFrame(rcTop,rcschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.HRSrc", prop)
    })
  
  lines3.foreachRDD(words =>
    {
    // Use Sort + Threshold to Implement
    val id3:String = dateFormat.format(new Date())
    words.map(a => a.map(b => println(b)))
    val IPSourceTop     = words.map(x => (x(1),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id3,p._2.trim,p._1.toInt))
    val IPDestTop	= words.map(x => (x(2),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id3,p._2.trim,p._1.toInt))
    val nameTop		= words.map(x => (x(3),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id3,p._2.trim,p._1.toInt))
    val typeTop		= words.map(x => (x(4),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id3,p._2.trim,p._1.toInt))
    sqlContext.createDataFrame(IPSourceTop,ipsschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.DRQips", prop)
    sqlContext.createDataFrame(IPDestTop,ipdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.DRQipd", prop)
    sqlContext.createDataFrame(nameTop,nameschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.DRQname", prop)
    sqlContext.createDataFrame(typeTop,typeschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.DRQtype", prop)
    })

  lines4.foreachRDD(words =>
    {
    // Use Sort + Threshold to Implement
    val id4:String = dateFormat.format(new Date())
    words.map(a => a.map(b => println(b)))
    val IPSourceTop     = words.map(x => (x(1),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id4,p._2.trim,p._1.toInt))
    val IPDestTop   = words.map(x => (x(2),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id4,p._2.trim,p._1.toInt))
    val nameTop       = words.map(x => (x(3),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id4,p._2.trim,p._1.toInt))
    val typeTop     = words.map(x => (x(4),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id4,p._2.trim,p._1.toInt))
    val urlTop     = words.map(x => (x(7),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>thre1).map(p => Row(id4,p._2.trim,p._1.toInt))
    sqlContext.createDataFrame(IPSourceTop,ipsschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.DRSips", prop)
    sqlContext.createDataFrame(IPDestTop,ipdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.DRSipd", prop)
    sqlContext.createDataFrame(nameTop,nameschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.DRSname", prop)
    sqlContext.createDataFrame(typeTop,typeschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.DRStype", prop)
    sqlContext.createDataFrame(urlTop,urlschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/stat", "stat.DRSurl", prop)
    })

  //Step final: start the spark streaming context
  ssc.start
  ssc.awaitTermination
  }
}
