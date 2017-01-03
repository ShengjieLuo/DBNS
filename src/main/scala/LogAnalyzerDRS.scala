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

object LogStreamingDRS {

def main(args:Array[String]){
  StreamingExamples.setStreamingLogLevels()
  
  //step0: Receive the information from Kafka
  val sc = new SparkConf().setAppName("LogStreamingDRS").setMaster("local[2]")
  val sssc = new SparkContext(sc)
  val ssc = new StreamingContext(sssc,Seconds(60))
  ssc.checkpoint("file:///usr/local/spark/mycode/DBNS/checkpoint3")
  val zkQuorum = "localhost:2182" //Zookeeper服务器地址
  val group = "1"  //topic所在的group，可以设置为自己想要的名称，比如不用1，而是val group = "test-consumer-group" 
  
  val topic = "dnsResponse"   //topics的名
  val numThreads = 1  //每个topic的分区数
  val topicMap =topic.split(",").map((_,numThreads.toInt)).toMap
  val lineMap = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap)
 
 
  //step1: Save the original information into the file system
  //Use the shell to verify result: (shell) cat other/HDFSsample.txt
  //Output is stored in the local filesystem now
  lineMap.saveAsTextFiles("file:///usr/local/spark/mycode/DBNS/backup/drs.txt")

  //Step final: start the spark streaming context
  ssc.start
  ssc.awaitTermination
  }
}
