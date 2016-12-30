import org.apache.spark._
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Put,Table,Result}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HTableDescriptor,HColumnDescriptor,HBaseConfiguration,TableName}
import java.io.File

object Off2HBase {

def main(args:Array[String]){
  val sc = new SparkConf().setAppName("KafkaLogCount").setMaster("local[2]")
  val sssc = new SparkContext(sc)
  val file = new File("/usr/local/spark/mycode/DBNS/backup")
  val name = "file://" + file.listFiles().filter(_.isDirectory()).map(a => a.getAbsolutePath().toString).sorted.last + "/part-*"
  val hbrdd = sssc.textFile(name)
  val hblines = hbrdd.map(_.split("\t"))
  val tablename = "HTTP_RESPONSE"
  sssc.hadoopConfiguration.set("hbase.zookeeper.quorum","localhost")
  sssc.hadoopConfiguration.set("hbase.zookeeper.property.clientPort", "2181")
  sssc.hadoopConfiguration.set(TableOutputFormat.OUTPUT_TABLE, tablename)
  val job = new Job(sssc.hadoopConfiguration)
  job.setOutputKeyClass(classOf[ImmutableBytesWritable])
  job.setOutputValueClass(classOf[Result])
  job.setOutputFormatClass(classOf[TableOutputFormat[ImmutableBytesWritable]])
  hblines.map(row => {
    val put = new Put(Bytes.toBytes(row(0).substring(6,row(0).length)))
    put.add(Bytes.toBytes("info"),Bytes.toBytes("TTL"),Bytes.toBytes(row(1)))
    put.add(Bytes.toBytes("info"),Bytes.toBytes("IPSource"),Bytes.toBytes(row(2)))
    put.add(Bytes.toBytes("info"),Bytes.toBytes("PortSource"),Bytes.toBytes(row(3)))
    put.add(Bytes.toBytes("info"),Bytes.toBytes("IPDest"),Bytes.toBytes(row(4)))
    put.add(Bytes.toBytes("info"),Bytes.toBytes("PortDest"),Bytes.toBytes(row(5)))
    put.add(Bytes.toBytes("info"),Bytes.toBytes("returnCode"),Bytes.toBytes(row(6)))
    (new ImmutableBytesWritable, put)
  }).saveAsNewAPIHadoopDataset(job.getConfiguration())
}}
