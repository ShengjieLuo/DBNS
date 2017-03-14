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
import java.io.File
import java.util.Date
import java.text.SimpleDateFormat

object OfflineAnalysis{

def main(args:Array[String]){
  StreamingExamples.setStreamingLogLevels()
  val sc = new SparkConf().setAppName("OfflineAnalysisCluster").setMaster("spark://spark-master:7077")
  val sssc = new SparkContext(sc)
  val sqlContext = new SQLContext(sssc)
  val hiveCtx = new HiveContext(sssc)
  val prop = new Properties()
  prop.put("user", "root")
  prop.put("password", "123456")
  prop.put("driver","com.mysql.jdbc.Driver")
  val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm") 
  val time:String = dateFormat.format(new Date())

  //Read the time of last batch from mysql dbns.offstatus
  //dbns.offstatus <analysis time, first time, last time>
  //Logic: Deal all of the data later than the last time of last batch
  var stamp:Long = System.currentTimeMillis()/1000;
  val forwardTimeStamp = stamp + 3600 - stamp%3600;
  val backTimeStamp = stamp - stamp%3600;
  if ((forwardTimeStamp - stamp) > (stamp - backTimeStamp)) {stamp = backTimeStamp -3600} else {stamp = forwardTimeStamp - 3600}
  val timestamp:Int = stamp.toInt 

  val lastDF = sqlContext.read.format("jdbc").options(Map("url" -> "jdbc:mysql://spark-master:3306/DBNS", "driver" -> "com.mysql.jdbc.Driver", "dbtable" -> "latest", "user" -> "root", "password" -> "123456")).load()
  val statusDF = sqlContext.read.format("jdbc").options(Map("url" -> "jdbc:mysql://spark-master:3306/DBNS", "driver" -> "com.mysql.jdbc.Driver", "dbtable" -> "offstatus", "user" -> "root", "password" -> "123456")).load()
  val lastTime = lastDF.take(1)(0)(0).toString.toInt
  val DRQlast = statusDF.filter("analysisTime = " + lastTime.toString).filter("item = 'DRQ'").take(1)(0)(3).toString
  val HRQlast = statusDF.filter("analysisTime = " + lastTime.toString).filter("item = 'HRQ'").take(1)(0)(3).toString
  val DRSlast = statusDF.filter("analysisTime = " + lastTime.toString).filter("item = 'DRS'").take(1)(0)(3).toString
  val HRSlast = statusDF.filter("analysisTime = " + lastTime.toString).filter("item = 'HRS'").take(1)(0)(3).toString
  val NETlast = statusDF.filter("analysisTime = " + lastTime.toString).filter("item = 'NET'").take(1)(0)(3).toString

  val HRQipd = hiveCtx.sql("SELECT FIRST(time),ipd,count(*) as count FROM hrq.original WHERE time> "+HRQlast+" GROUP BY ipd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val HRQips = hiveCtx.sql("SELECT FIRST(time),ips,count(*) as count FROM hrq.original WHERE time> "+HRQlast+" GROUP BY ips having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val HRQpd = hiveCtx.sql("SELECT FIRST(time),pd,count(*) as count FROM hrq.original WHERE time> "+HRQlast+" GROUP BY pd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val HRQps = hiveCtx.sql("SELECT FIRST(time),ps,count(*) as count FROM hrq.original WHERE time> "+HRQlast+" GROUP BY ps having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val HRQipds = hiveCtx.sql("SELECT ipd,cast(sum(TTL) as bigint) as sum FROM hrq.original WHERE time> "+HRQlast+" GROUP BY ipd ORDER BY sum DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(0).toString(),p(1).toString().toLong))
  val HRQipss = hiveCtx.sql("SELECT ips,cast(sum(TTL) as bigint) as sum FROM hrq.original WHERE time> "+HRQlast+" GROUP BY ips ORDER BY sum DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(0).toString(),p(1).toString().toLong))
  val HRQtype = hiveCtx.sql("SELECT type,count(*) as count FROM hrq.original WHERE time> "+HRQlast+" GROUP BY type ORDER BY count DESC").rdd.map(p=>Row(timestamp,p(0).toString(),p(1).toString().toInt))
  val HRQtypes = hiveCtx.sql("SELECT type,cast(sum(TTL) as bigint) as sum FROM hrq.original WHERE time> "+HRQlast+" GROUP BY type ORDER BY sum DESC").rdd.map(p=>Row(timestamp,p(0).toString(),p(1).toString().toLong))
  val HRQcount = hiveCtx.sql("SELECT count(*) FROM hrq.original WHERE time> "+HRQlast).rdd.map(p=>Row(timestamp,p(0).toString().toLong))
  val HRQsum = hiveCtx.sql("SELECT cast(sum(TTL) as bigint) FROM hrq.original WHERE time> "+HRQlast).rdd.map(p=>Row(timestamp,p(0).toString().toLong))
  val HRQlastTime = hiveCtx.sql("SELECT MIN(time),MAX(time) FROM HRQ.original WHERE time> "+HRQlast).rdd.map(p=>Row(timestamp,"HRQ",p(0).toString().toInt,p(1).toString().toInt))
    
  val HRSipd = hiveCtx.sql("SELECT FIRST(time),ipd,count(*) as count FROM hrs.original WHERE time> "+HRSlast+" GROUP BY ipd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val HRSips = hiveCtx.sql("SELECT FIRST(time),ips,count(*) as count FROM hrs.original WHERE time> "+HRSlast+" GROUP BY ips having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val HRSpd = hiveCtx.sql("SELECT FIRST(time),pd,count(*) as count FROM hrs.original WHERE time> "+HRSlast+" GROUP BY pd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val HRSps = hiveCtx.sql("SELECT FIRST(time),ps,count(*) as count FROM hrs.original WHERE time> "+HRSlast+" GROUP BY ps having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val HRSrc = hiveCtx.sql("SELECT FIRST(time),rc,count(*) as count FROM hrs.original WHERE time> "+HRSlast+" GROUP BY rc having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val HRSrcs = hiveCtx.sql("SELECT FIRST(time),rc,cast(sum(TTL) as bigint) as sum FROM hrs.original WHERE time> "+HRSlast+" GROUP BY rc ORDER BY sum DESC").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toLong))
  val HRSipds = hiveCtx.sql("SELECT ipd,cast(sum(TTL) as bigint) as sum FROM hrs.original WHERE time> "+HRSlast+" GROUP BY ipd ORDER BY sum DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(0).toString(),p(1).toString().toLong))
  val HRSipss = hiveCtx.sql("SELECT ips,cast(sum(TTL) as bigint) as sum FROM hrs.original WHERE time> "+HRSlast+" GROUP BY ips ORDER BY sum DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(0).toString(),p(1).toString().toLong))
  val HRScount = hiveCtx.sql("SELECT count(*) FROM hrs.original WHERE time> "+HRSlast).rdd.map(p=>Row(timestamp,p(0).toString().toLong))
  val HRSsum = hiveCtx.sql("SELECT cast(sum(TTL) as bigint) FROM hrs.original WHERE time> "+HRSlast).rdd.map(p=>Row(timestamp,p(0).toString().toLong))
  val HRSlastTime = hiveCtx.sql("SELECT MIN(time),MAX(time) FROM HRS.original WHERE time> "+HRSlast).rdd.map(p=>Row(timestamp,"HRS",p(0).toString().toInt,p(1).toString().toInt))
  
  val DRQips = hiveCtx.sql("SELECT FIRST(time),ips,count(*) as count FROM drq.original WHERE time> "+DRQlast+" GROUP BY ips having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val DRQipd = hiveCtx.sql("SELECT FIRST(time),ipd,count(*) as count FROM drq.original WHERE time> "+DRQlast+" GROUP BY ipd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val DRQname = hiveCtx.sql("SELECT FIRST(time),name,count(*) as count FROM drq.original WHERE time> "+DRQlast+" GROUP BY name having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val DRQtype = hiveCtx.sql("SELECT FIRST(time),type,count(*) as count FROM drq.original WHERE time> "+DRQlast+" GROUP BY type having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val DRQcount = hiveCtx.sql("SELECT count(*) FROM drq.original WHERE time> "+DRQlast).rdd.map(p=>Row(timestamp,p(0).toString().toLong))
  val DRQlastTime = hiveCtx.sql("SELECT MIN(time),MAX(time) FROM DRQ.original WHERE time> "+DRQlast).rdd.map(p=>Row(timestamp,"DRQ",p(0).toString().toInt,p(1).toString().toInt))

  val DRSips = hiveCtx.sql("SELECT FIRST(time),ips,count(*) as count FROM drs.original WHERE time> "+DRSlast+" GROUP BY ips having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val DRSipd = hiveCtx.sql("SELECT FIRST(time),ipd,count(*) as count FROM drs.original WHERE time> "+DRSlast+" GROUP BY ipd having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val DRSname = hiveCtx.sql("SELECT FIRST(time),name,count(*) as count FROM drs.original WHERE time> "+DRSlast+" GROUP BY name having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val DRStype = hiveCtx.sql("SELECT FIRST(time),type,count(*) as count FROM drs.original WHERE time> "+DRSlast+" GROUP BY type having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val DRSurl = hiveCtx.sql("SELECT FIRST(time),url,count(*) as count FROM drs.original WHERE time> "+DRSlast+" GROUP BY url having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(1).toString(),p(2).toString().toInt))
  val DRScount = hiveCtx.sql("SELECT count(*) FROM drs.original WHERE time> "+DRSlast).rdd.map(p=>Row(timestamp,p(0).toString().toLong))
  val DRSlastTime = hiveCtx.sql("SELECT MIN(time),MAX(time) FROM DRS.original WHERE time> "+DRSlast).rdd.map(p=>Row(timestamp,"DRS",p(0).toString().toInt,p(1).toString().toInt))


  val NETipd = hiveCtx.sql("SELECT dst_ip,sum(packets) as count FROM net.original WHERE time> "+NETlast+" GROUP BY dst_ip having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(0).toString(),p(1).toString().toFloat.toInt))
  val NETips = hiveCtx.sql("SELECT src_ip,sum(packets) as count FROM net.original WHERE time> "+NETlast+" GROUP BY src_ip having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(0).toString(),p(1).toString().toFloat.toInt))
  val NETtype = hiveCtx.sql("SELECT protocol,sum(packets) as count FROM net.original WHERE time> "+NETlast+" GROUP BY protocol having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(0).toString(),p(1).toString().toFloat.toInt))
  val NETipds = hiveCtx.sql("SELECT dst_ip,cast(sum(bytes) as bigint) as count FROM net.original WHERE time> "+NETlast+" GROUP BY dst_ip having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(0).toString(),p(1).toString().toDouble.toLong))
  val NETipss = hiveCtx.sql("SELECT src_ip,cast(sum(bytes) as bigint) as count FROM net.original WHERE time> "+NETlast+" GROUP BY src_ip having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(0).toString(),p(1).toString().toDouble.toLong))
  val NETtypes = hiveCtx.sql("SELECT protocol,cast(sum(bytes) as bigint) as count FROM net.original WHERE time> "+NETlast+" GROUP BY protocol having count > 100 ORDER BY count DESC LIMIT 50").rdd.map(p=>Row(timestamp,p(0).toString(),p(1).toString().toDouble.toLong))
  val NETcount = hiveCtx.sql("SELECT sum(packets) FROM net.original WHERE time> "+NETlast).rdd.map(p=>Row(timestamp,p(0).toString().toDouble.toLong))
  val NETsum = hiveCtx.sql("SELECT sum(bytes) FROM net.original WHERE time> "+NETlast).rdd.map(p=>Row(timestamp,p(0).toString().toDouble.toLong))
  val NETlastTime = hiveCtx.sql("SELECT MIN(time),MAX(time) FROM net.original WHERE time> "+HRQlast).rdd.map(p=>Row(timestamp,"NET",p(0).toString().toInt,p(1).toString().toInt))

  val ipsschema = StructType(List(StructField("time",IntegerType,true),StructField("IPSource",StringType,true),StructField("count",IntegerType,true)))
  val ipdschema = StructType(List(StructField("time",IntegerType,true),StructField("IPDest",StringType,true),StructField("count",IntegerType,true)))
  val nameschema  = StructType(List(StructField("time",IntegerType,true),StructField("name",StringType,true),StructField("count",IntegerType,true)))
  val typeschema  = StructType(List(StructField("time",IntegerType,true),StructField("type",StringType,true),StructField("count",IntegerType,true)))
  val typesschema = StructType(List(StructField("time",IntegerType,true),StructField("type",StringType,true),StructField("size",LongType,true)))
  val psschema  = StructType(List(StructField("time",IntegerType,true),StructField("PortSource",StringType,true),StructField("count",IntegerType,true)))
  val pdschema  = StructType(List(StructField("time",IntegerType,true),StructField("PortDest",StringType,true),StructField("count",IntegerType,true)))
  val urlschema  = StructType(List(StructField("time",IntegerType,true),StructField("url",StringType,true),StructField("count",IntegerType,true)))
  val rcschema  = StructType(List(StructField("time",IntegerType,true),StructField("returnCode",StringType,true),StructField("count",IntegerType,true)))
  val rcsschema  = StructType(List(StructField("time",IntegerType,true),StructField("returnCode",StringType,true),StructField("size",LongType,true)))
  val lastschema = StructType(List(StructField("analysisTime",IntegerType,true),StructField("item",StringType,true),StructField("firsttime",IntegerType,true),StructField("lasttime",IntegerType,true)))
  val allschema = StructType(List(StructField("time",IntegerType,true),StructField("count",LongType,true)))
  val sizeschema = StructType(List(StructField("time",IntegerType,true),StructField("IP",StringType,true),StructField("size",LongType,true)))

  val timeRow = sssc.parallelize(List(timestamp.toInt)).map(p=>Row(p))
  val timeschema = StructType(List(StructField("time",IntegerType,true)))
  sqlContext.createDataFrame(timeRow,timeschema).write.mode("overwrite").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.latest", prop)
  
  sqlContext.createDataFrame(HRQips,ipsschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRQips", prop)
  sqlContext.createDataFrame(HRQps,psschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRQps", prop)
  sqlContext.createDataFrame(HRQipd,ipdschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRQipd", prop)
  sqlContext.createDataFrame(HRQpd,pdschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRQpd", prop)
  sqlContext.createDataFrame(HRQipss,sizeschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRQipss", prop)
  sqlContext.createDataFrame(HRQipds,sizeschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRQipds", prop)
  sqlContext.createDataFrame(HRQtype,typeschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRQtype", prop)
  sqlContext.createDataFrame(HRQtypes,typesschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRQtypes", prop)
  sqlContext.createDataFrame(HRQcount,allschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRQcount", prop)
  sqlContext.createDataFrame(HRQsum,allschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRQsum", prop)
  
  sqlContext.createDataFrame(HRSips,ipsschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRSips", prop)
  sqlContext.createDataFrame(HRSps,psschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRSps", prop)
  sqlContext.createDataFrame(HRSipd,ipdschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRSipd", prop)
  sqlContext.createDataFrame(HRSpd,pdschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRSpd", prop)
  sqlContext.createDataFrame(HRSrc,rcschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRSrc", prop)
  sqlContext.createDataFrame(HRSrcs,rcsschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRSrcs", prop)
  sqlContext.createDataFrame(HRSipss,sizeschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRSipss", prop)
  sqlContext.createDataFrame(HRSipds,sizeschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRSipds", prop)
  sqlContext.createDataFrame(HRScount,allschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRScount", prop)
  sqlContext.createDataFrame(HRSsum,allschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.HRSsum", prop)
  
  sqlContext.createDataFrame(DRQips,ipsschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.DRQips", prop)
  sqlContext.createDataFrame(DRQipd,ipdschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.DRQipd", prop)
  sqlContext.createDataFrame(DRQname,nameschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.DRQname", prop)
  sqlContext.createDataFrame(DRQtype,typeschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.DRQtype", prop)
  sqlContext.createDataFrame(DRQcount,allschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.DRQcount", prop)
  
  sqlContext.createDataFrame(DRSips,ipsschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.DRSips", prop)
  sqlContext.createDataFrame(DRSipd,ipdschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.DRSipd", prop)
  sqlContext.createDataFrame(DRSname,nameschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.DRSname", prop)
  sqlContext.createDataFrame(DRStype,typeschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.DRStype", prop)
  sqlContext.createDataFrame(DRSurl,urlschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.DRSurl", prop)
  sqlContext.createDataFrame(DRScount,allschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.DRScount", prop)

  sqlContext.createDataFrame(NETips,ipsschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.NETips", prop)
  sqlContext.createDataFrame(NETipd,ipdschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.NETipd", prop)
  sqlContext.createDataFrame(NETipss,sizeschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.NETipss", prop)
  sqlContext.createDataFrame(NETipds,sizeschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.NETipds", prop)
  sqlContext.createDataFrame(NETtype,typeschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.NETtype", prop)
  sqlContext.createDataFrame(NETtypes,typesschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.NETtypes", prop)
  sqlContext.createDataFrame(NETcount,allschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.NETcount", prop)
  sqlContext.createDataFrame(NETsum,allschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.NETsum", prop)
 
  sqlContext.createDataFrame(HRQlastTime,lastschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.offstatus", prop)
  sqlContext.createDataFrame(DRQlastTime,lastschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.offstatus", prop)
  sqlContext.createDataFrame(HRSlastTime,lastschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.offstatus", prop)
  sqlContext.createDataFrame(DRSlastTime,lastschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.offstatus", prop)
  sqlContext.createDataFrame(NETlastTime,lastschema).write.mode("append").jdbc("jdbc:mysql://spark-master:3306/DBNS", "DBNS.offstatus", prop)
    
  }
}
