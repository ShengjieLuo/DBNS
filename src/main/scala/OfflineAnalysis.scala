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

object OfflineAnalysis{

def main(args:Array[String]){
  StreamingExamples.setStreamingLogLevels()
  val sc = new SparkConf().setAppName("LogStreamingAdvancedSparkCluster").setMaster("spark://172.16.0.104:7077")
  val sssc = new SparkContext(sc)
  val sqlContext = new SQLContext(sssc)
  val hiveCtx = new HiveContext(sssc)
  val prop = new Properties()
  prop.put("user", "root")
  prop.put("password", "123456")
  prop.put("driver","com.mysql.jdbc.Driver")
  val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm") 
  val time:String = dateFormat.format(new Date())
  
  val HRQipd = hiveCtx.sql("SELECT ipd,count(*) as count FROM hrq.original GROUP BY ipd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val HRQips = hiveCtx.sql("SELECT ips,count(*) as count FROM hrq.original GROUP BY ips having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val HRQpd = hiveCtx.sql("SELECT pd,count(*) as count FROM hrq.original GROUP BY pd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val HRQps = hiveCtx.sql("SELECT ps,count(*) as count FROM hrq.original GROUP BY ps having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
    
  val HRSipd = hiveCtx.sql("SELECT ipd,count(*) as count FROM hrs.original GROUP BY ipd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val HRSips = hiveCtx.sql("SELECT ips,count(*) as count FROM hrs.original GROUP BY ips having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val HRSpd = hiveCtx.sql("SELECT pd,count(*) as count FROM hrs.original GROUP BY pd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val HRSps = hiveCtx.sql("SELECT ps,count(*) as count FROM hrs.original GROUP BY ps having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val HRSrc = hiveCtx.sql("SELECT rc,count(*) as count FROM hrs.original GROUP BY rc having count > 100 ORDER BY count DESC LIMIT 50;").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  
  val DRQips = hiveCtx.sql("SELECT ips,count(*) as count FROM drq.original GROUP BY ips having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val DRQipd = hiveCtx.sql("SELECT ipd,count(*) as count FROM drq.original GROUP BY ipd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val DRQname = hiveCtx.sql("SELECT name,count(*) as count FROM drq.original GROUP BY name having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val DRQtype = hiveCtx.sql("SELECT type,count(*) as count FROM drq.original GROUP BY type having count > 100 ORDER BY count DESC LIMIT 50").rdd

  
  val DRSips = hiveCtx.sql("SELECT ips,count(*) as count FROM drs.original GROUP BY ips having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val DRSipd = hiveCtx.sql("SELECT ipd,count(*) as count FROM drs.original GROUP BY ipd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val DRSname = hiveCtx.sql("SELECT name,count(*) as count FROM drs.original GROUP BY name having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt)) 
  val DRStype = hiveCtx.sql("SELECT type,count(*) as count FROM drs.original GROUP BY type having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))
  val DRSurl = hiveCtx.sql("SELECT url,count(*) as count FROM drs.original GROUP BY url having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p => Row(time,p._2.trim,p._1.toInt))

  val ipsschema = StructType(List(StructField("id",StringType,true),StructField("IPSource",StringType,true),StructField("count",IntegerType,true)))
  val ipdschema = StructType(List(StructField("id",StringType,true),StructField("IPDest",StringType,true),StructField("count",IntegerType,true)))
  val nameschema  = StructType(List(StructField("id",StringType,true),StructField("name",StringType,true),StructField("count",IntegerType,true)))
  val typeschema  = StructType(List(StructField("id",StringType,true),StructField("type",StringType,true),StructField("count",IntegerType,true)))
  val psschema  = StructType(List(StructField("id",StringType,true),StructField("PortSource",StringType,true),StructField("count",IntegerType,true)))
  val pdschema  = StructType(List(StructField("id",StringType,true),StructField("PortDest",StringType,true),StructField("count",IntegerType,true)))
  val urlschema  = StructType(List(StructField("id",StringType,true),StructField("url",StringType,true),StructField("count",IntegerType,true)))
  val rcschema  = StructType(List(StructField("id",StringType,true),StructField("returnCode",StringType,true),StructField("count",IntegerType,true)))

  sqlContext.createDataFrame(IPSourceTop,ipsschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.HRQips", prop)
  sqlContext.createDataFrame(PortSourceTop,psschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.HRQps", prop)
  sqlContext.createDataFrame(IPDestTop,ipdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.HRQipd", prop)
  sqlContext.createDataFrame(PortDestTop,pdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.HRQpd", prop)
  sqlContext.createDataFrame(IPSourceTop,ipsschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.HRSips", prop)
  sqlContext.createDataFrame(PortSourceTop,psschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.HRSps", prop)
  sqlContext.createDataFrame(IPDestTop,ipdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.HRSipd", prop)
  sqlContext.createDataFrame(PortDestTop,pdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.HRSpd", prop)
  sqlContext.createDataFrame(rcTop,rcschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.HRSrc", prop)
  sqlContext.createDataFrame(IPSourceTop,ipsschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.DRQips", prop)
  sqlContext.createDataFrame(IPDestTop,ipdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.DRQipd", prop)
  sqlContext.createDataFrame(nameTop,nameschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.DRQname", prop)
  sqlContext.createDataFrame(typeTop,typeschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.DRQtype", prop)
  sqlContext.createDataFrame(IPSourceTop,ipsschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.DRSips", prop)
  sqlContext.createDataFrame(IPDestTop,ipdschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.DRSipd", prop)
  sqlContext.createDataFrame(nameTop,nameschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.DRSname", prop)
  sqlContext.createDataFrame(typeTop,typeschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.DRStype", prop)
  sqlContext.createDataFrame(urlTop,urlschema).write.mode("append").jdbc("jdbc:mysql://172.16.0.104:3306/DBNS", "DBNS.DRSurl", prop)

  //Step final: start the spark streaming context
  ssc.start
  ssc.awaitTermination
  }
}
