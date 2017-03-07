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

object OnlineQuery{

def main(args:Array[String]){
  StreamingExamples.setStreamingLogLevels()
  val sc = new SparkConf().setAppName("OnlineQuery").setMaster("spark://spark-master:7077")
  val sssc = new SparkContext(sc)
  val ssc = new StreamingContext(sssc,Seconds(60))
  val sqlContext = new SQLContext(sssc)
  val hiveCtx = new HiveContext(sssc)
  val prop = new Properties()
  prop.put("user", "root")
  prop.put("password", "123456")
  prop.put("driver","com.mysql.jdbc.Driver")
  val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm") 
  val time:String = dateFormat.format(new Date())
  
  // Read the auguments
  val tablename:String = args(0)
  val typename:String = args(1)
  val starttime:String = args(2)
  val endtime:String = args(3)
  
  // Initial the SQL schema
  val ipsschema = StructType(List(StructField("id",StringType,true),StructField("IPSource",StringType,true),StructField("count",IntegerType,true)))
  val ipdschema = StructType(List(StructField("id",StringType,true),StructField("IPDest",StringType,true),StructField("count",IntegerType,true)))
  val nameschema  = StructType(List(StructField("id",StringType,true),StructField("name",StringType,true),StructField("count",IntegerType,true)))
  val typeschema  = StructType(List(StructField("id",StringType,true),StructField("type",StringType,true),StructField("count",IntegerType,true)))
  val psschema  = StructType(List(StructField("id",StringType,true),StructField("PortSource",StringType,true),StructField("count",IntegerType,true)))
  val pdschema  = StructType(List(StructField("id",StringType,true),StructField("PortDest",StringType,true),StructField("count",IntegerType,true)))
  val urlschema  = StructType(List(StructField("id",StringType,true),StructField("url",StringType,true),StructField("count",IntegerType,true)))
  val rcschema  = StructType(List(StructField("id",StringType,true),StructField("returnCode",StringType,true),StructField("count",IntegerType,true)))
  
  if (tablename=="HRQ" & typename=="top")
{
  val HRQipd = hiveCtx.sql("SELECT ipd,count(*) as count FROM hrq.original WHERE "+starttime+" <time and time<" + endtime +" GROUP BY ipd ORDER BY count DESC LIMIT 10").rdd.map(p=>Row(p(0).toString(),p(1).toString().toInt))
  val HRQips = hiveCtx.sql("SELECT ips,count(*) as count FROM hrq.original WHERE "+starttime+" <time and time<" + endtime +" GROUP BY ips ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString().toInt))
  val HRQpd = hiveCtx.sql("SELECT pd,count(*) as count FROM hrq.original WHERE "+starttime+" <time and time<" + endtime +" GROUP BY pd ORDER BY count DESC LIMIT 10").rdd.map(p=>Row(p(0).toString(),p(1).toString().toInt))
  val HRQps = hiveCtx.sql("SELECT ps,count(*) as count FROM hrq.original WHERE "+starttime+" <time and time<" + endtime +" GROUP BY ps ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString().toInt))
  sqlContext.createDataFrame(HRQips,ipsschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.HRQips", prop)
  sqlContext.createDataFrame(HRQps,psschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.HRQps", prop)
  sqlContext.createDataFrame(HRQipd,ipdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.HRQipd", prop)
  sqlContext.createDataFrame(HRQpd,pdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.HRQpd", prop)
}

  if (tablename=="HRS" & typenmae=="top")
{  
  val HRSipd = hiveCtx.sql("SELECT FIRST(time),ipd,count(*) as count FROM hrs.original GROUP BY ipd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  val HRSips = hiveCtx.sql("SELECT FIRST(time),ips,count(*) as count FROM hrs.original GROUP BY ips having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  val HRSpd = hiveCtx.sql("SELECT FIRST(time),pd,count(*) as count FROM hrs.original GROUP BY pd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  val HRSps = hiveCtx.sql("SELECT FIRST(time),ps,count(*) as count FROM hrs.original GROUP BY ps having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  val HRSrc = hiveCtx.sql("SELECT FIRST(time),rc,count(*) as count FROM hrs.original GROUP BY rc having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  sqlContext.createDataFrame(HRSips,ipsschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.HRSips", prop)
  sqlContext.createDataFrame(HRSps,psschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.HRSps", prop)
  sqlContext.createDataFrame(HRSipd,ipdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.HRSipd", prop)
  sqlContext.createDataFrame(HRSpd,pdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.HRSpd", prop)
  sqlContext.createDataFrame(HRSrc,rcschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.HRSrc", prop)
}

  if (tablename=="DRQ" & typename=="top")
{ 
  val DRQips = hiveCtx.sql("SELECT FIRST(time),ips,count(*) as count FROM drq.original GROUP BY ips having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  val DRQipd = hiveCtx.sql("SELECT FIRST(time),ipd,count(*) as count FROM drq.original GROUP BY ipd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  val DRQname = hiveCtx.sql("SELECT FIRST(time),name,count(*) as count FROM drq.original GROUP BY name having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  val DRQtype = hiveCtx.sql("SELECT FIRST(time),type,count(*) as count FROM drq.original GROUP BY type having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  sqlContext.createDataFrame(DRQips,ipsschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.DRQips", prop)
  sqlContext.createDataFrame(DRQipd,ipdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.DRQipd", prop)
  sqlContext.createDataFrame(DRQname,nameschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.DRQname", prop)
  sqlContext.createDataFrame(DRQtype,typeschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.DRQtype", prop)
}

  if (tablename=="DRS" & typename=="top")
{
  val DRSips = hiveCtx.sql("SELECT FIRST(time),ips,count(*) as count FROM drs.original GROUP BY ips having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  val DRSipd = hiveCtx.sql("SELECT FIRST(time),ipd,count(*) as count FROM drs.original GROUP BY ipd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  val DRSname = hiveCtx.sql("SELECT FIRST(time),name,count(*) as count FROM drs.original GROUP BY name having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  val DRStype = hiveCtx.sql("SELECT FIRST(time),type,count(*) as count FROM drs.original GROUP BY type having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  val DRSurl = hiveCtx.sql("SELECT FIRST(time),url,count(*) as count FROM drs.original GROUP BY url having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(p(0).toString(),p(1).toString(),p(2).toString().toInt))
  sqlContext.createDataFrame(DRSips,ipsschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.DRSips", prop)
  sqlContext.createDataFrame(DRSipd,ipdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.DRSipd", prop)
  sqlContext.createDataFrame(DRSname,nameschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.DRSname", prop)
  sqlContext.createDataFrame(DRStype,typeschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.DRStype", prop)
  sqlContext.createDataFrame(DRSurl,urlschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/query", "query.DRSurl", prop)
}


  //Step final: start the spark streaming context
  ssc.start
  ssc.awaitTermination
  }
}
