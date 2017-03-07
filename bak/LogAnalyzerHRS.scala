import org.apache.hadoop.hbase.HBaseConfiguration  
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapred.JobConf
import org.apache.spark._
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.io.ImmutableBytesWritable  
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Put,Table,Result}
import org.apache.hadoop.hbase.util.Bytes  
import org.apache.hadoop.hbase.{HTableDescriptor,HColumnDescriptor,HBaseConfiguration,TableName}
import org.apache.spark.rdd.RDD
import java.util.Calendar
import org.apache.commons.codec.binary.Base64
import org.apache.http.HttpResponse
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.{HttpGet, HttpPut}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients
import org.apache.log4j.Logger
import java.util.Properties
import org.apache.spark.sql.{SQLContext, Row}
import org.apache.spark.sql.types._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.hive.HiveContext._
import java.io.File
//import hiveContext.implicits._

object LogStreamingHRS {

def main(args:Array[String]){
  StreamingExamples.setStreamingLogLevels()
  
  //step0: Receive the information from Kafka
  val sc = new SparkConf().setAppName("LogStreamingHRS").setMaster("local[2]")
  val sssc = new SparkContext(sc)
  val ssc = new StreamingContext(sssc,Seconds(60))
  ssc.checkpoint("file:///usr/local/spark/mycode/DBNS/checkpoint")
  val zkQuorum = "localhost:2182" //Zookeeper服务器地址
  val group = "1"  //topic所在的group，可以设置为自己想要的名称，比如不用1，而是val group = "test-consumer-group" 
  
  val topic = "httpResponse"   //topics的名
  val numThreads = 1  //每个topic的分区数
  val topicMap =topic.split(",").map((_,numThreads.toInt)).toMap
  val lineMap = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap)
 
 
  //step1: Save the original information into the file system
  //Use the shell to verify result: (shell) cat other/HDFSsample.txt
  //Output is stored in the local filesystem now
  lineMap.saveAsTextFiles("file:///usr/local/spark/mycode/DBNS/backup/hrs.txt")

  //step3: Write the original information into the Hive within SparkSQL
  val lines = lineMap.map(_._2).map(_.split("\t"))
  val hiveCtx = new HiveContext(sssc)
  val schema = StructType(List(StructField("time", StringType, true),StructField("TTL", StringType, true),StructField("ips", StringType, true),StructField("ps", StringType, true),StructField("ipd", StringType, true),StructField("pd", StringType, true),StructField("rc", StringType, true)))
  lines.foreachRDD(rdd =>
  {
    val rowrdd = hiveCtx.createDataFrame(rdd.map(p => Row(p(0).trim, p(1).trim, p(2).trim, p(3).trim,p(4).trim,p(5).trim,p(6).trim)), schema)
    //rowrdd.map(p=>println(p))
    rowrdd.registerTempTable("tempTable")
    hiveCtx.sql("insert into HRS.original select * from tempTable")
  })

  //Step4: Write the statistical data into the SparkSQL --> mySQL
  val sqlContext = new SQLContext(sssc)
  val ipsschema = StructType(List(StructField("IPSource",StringType,true),StructField("count",IntegerType,true)))
  val psschema  = StructType(List(StructField("PortSource",StringType,true),StructField("count",IntegerType,true)))
  val ipdschema = StructType(List(StructField("IPDest",StringType,true),StructField("count",IntegerType,true)))
  val pdschema  = StructType(List(StructField("PortDest",StringType,true),StructField("count",IntegerType,true)))
  val prop = new Properties()
  prop.put("user", "root")
  prop.put("password", "123456")
  prop.put("driver","com.mysql.jdbc.Driver")
  lines.foreachRDD(words =>
    {
    // Use Sort + Threshold to Implement
    val IPSourceTop     = words.map(x => (x(2),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>5).map(p => Row(p._2.trim,p._1.toInt))
    val PortSourceTop   = words.map(x => (x(3),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>5).map(p => Row(p._2.trim,p._1.toInt))
    val IPDestTop       = words.map(x => (x(4),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>5).map(p => Row(p._2.trim,p._1.toInt))
    val PortDestTop     = words.map(x => (x(5),1)).reduceByKey((x,y) => x + y).map(p => (p._2,p._1)).sortByKey().filter(_._1>5).map(p => Row(p._2.trim,p._1.toInt))
    sqlContext.createDataFrame(IPSourceTop,ipsschema).write.mode("append").jdbc("jdbc:mysql://localhost:3306/stat", "stat.HRQips", prop)
    sqlContext.createDataFrame(PortSourceTop,psschema).write.mode("append").jdbc("jdbc:mysql://localhost:3306/stat", "stat.HRQps", prop)
    sqlContext.createDataFrame(IPDestTop,ipdschema).write.mode("append").jdbc("jdbc:mysql://localhost:3306/stat", "stat.HRQipd", prop)
    sqlContext.createDataFrame(PortDestTop,pdschema).write.mode("append").jdbc("jdbc:mysql://localhost:3306/stat", "stat.HRQpd", prop)
    })

  //Step final: start the spark streaming context
  ssc.start
  ssc.awaitTermination
  }
}
