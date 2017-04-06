package com.service

import com.convert.Convertor
import com.model.other.Item
import com.model.other.Time
import com.model.other.Request

import java.util.HashMap
import org.apache.kafka.clients.producer.{ProducerConfig, KafkaProducer, ProducerRecord}
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf
import scala.io.Source

class TCPservice(itemobj:String) {

 
  val brokers = "spark-master:9092"
  val topic = "internal" 
  val obj:String = itemobj

  val producer = {
    val props = new HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](props) 
  }

  def send_to_queue(content:List[String]){
    content.foreach( line => {
      val message = new ProducerRecord[String, String](topic, null, line)
      producer.send(message) })
  }
  

  //def wait_from_queue(content:)
  
  //TCP service 1
  //Statistic the upload TCP size of specific IP
  def SS_TCP_UPLOAD_SIZE_SRCIP(time:Time,other:String){
     //var para:List[String] = obj.toString :: time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var request:Request = new Request()
     request.setTime(time)
     request.setName("TCP service 1")
     //request.setParent(external.num)
     request.setRequestMode(other) 
     var external = new Convertor("SS_TCP_UPLOAD_SIZE_SRCIP",request)
     var internal:List[Request] = external.getInterface()
     //send_to_queue(internal)
  }

  /*
  //TCP service 2
  //Statistic the download TCP size of specific IP
  def SS_TCP_DOWNLOAD_SIZE_DSTIP(time:Time,other:String){
     var para:List[String] = obj.toString :: time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SS_TCP_DOWNLOAD_SIZE_DSTIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)  
  }

  //TCP service 3
  //Statistic the upload TCP size of specific IP
  def SS_TCP_UPLOAD_COUNT_SRCIP(time:Time,other:String){
     var para:List[String] = obj.toString :: time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SS_TCP_UPLOAD_COUNT_SRCIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }

  //TCP service 4
  //Statistic the download TCP size of specific IP
  def SS_TCP_DOWNLOAD_COUNT_DSTIP(time:Time,other:String){
     var para:List[String] = obj.toString :: time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SS_TCP_DOWNLOAD_COUNT_DSTIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }

  //TCP service 5
  //Statistic the upload TCP size of all IP
  def SA_TCP_UPLOAD_SIZE_GROUPBY_SRCIP(time:Time,other:String){
     var para:List[String] = time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SA_TCP_UPLOAD_SIZE_GROUPBY_SRCIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)  
  }
 
  //TCP service 6
  //Statistic the upload TCP size totally
  def SA_TCP_UPLOAD_SIZE_ALL_SRCIP(time:Time,other:String){
     var para:List[String] = time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SA_TCP_UPLOAD_SIZE_ALL_SRCIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)  
  }
  
  //TCP service 7
  //Statistic the upload TCP size in average
  def SA_TCP_UPLOAD_SIZE_AVERAGE_SRCIP(time:Time,other:String){
     var para:List[String] = time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SA_TCP_UPLOAD_SIZE_AVERAGE_SRCIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }

  //TCP service 8
  //Statistic the download TCP size of all IP
  def SA_TCP_DOWNLOAD_SIZE_GROUPBY_DSTIP(time:Time,other:String){
     var para:List[String] = time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SA_TCP_DOWNLOAD_SIZE_GROUPBY_DSTIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)  
  }

  //TCP service 9
  //Statistic the upload TCP size totally
  def SA_TCP_DOWNLOAD_SIZE_ALL_DSTIP(time:Time,other:String){
     var para:List[String] = time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SA_TCP_DOWNLOAD_SIZE_ALL_DSTIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)  
  }

  //TCP service 10
  //Statistic the upload TCP size totally
  def SA_TCP_DOWNLOAD_SIZE_AVERAGE_DSTIP(time:Time,other:String){
     var para:List[String] = time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SA_TCP_DOWNLOAD_SIZE_AVERAGE_DSTIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }

  //TCP service 11
  //Statistic the upload TCP size of all IP
  def SA_TCP_UPLOAD_COUNT_GROUPBY_SRCIP(time:Time,other:String){
     var para:List[String] = time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SA_TCP_UPLOAD_COUNT_GROUPBY_SRCIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }

  //TCP service 12
  //Statistic the upload TCP size totally
  def SA_TCP_UPLOAD_COUNT_ALL_SRCIP(time:Time,other:String){
     var para:List[String] = time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SA_TCP_UPLOAD_COUNT_ALL_SRCIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }

  //TCP service 13
  //Statistic the upload TCP size in average
  def SA_TCP_UPLOAD_COUNT_AVERAGE_SRCIP(time:Time,other:String){
     var para:List[String] = time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SA_TCP_UPLOAD_COUNT_AVERAGE_SRCIP")
     var internal:List[String] = external.getInterface()
     //Only For Test: Thread.sleep(30000)
     send_to_queue(internal)
  }

  //TCP service 14
  //Statistic the download TCP size of all IP
  def SA_TCP_DOWNLOAD_COUNT_GROUPBY_DSTIP(time:Time,other:String){
     var para:List[String] = time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SA_TCP_DOWNLOAD_COUNT_GROUPBY_DSTIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }

  //TCP service 15
  //Statistic the upload TCP size totally
  def SA_TCP_DOWNLOAD_COUNT_ALL_DSTIP(time:Time,other:String){
     var para:List[String] = time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SA_TCP_DOWNLOAD_COUNT_ALL_DSTIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }

  //TCP service 16
  //Statistic the upload TCP size totally
  def SA_TCP_DOWNLOAD_COUNT_AVERAGE_DSTIP(time:Time,other:String){
     var para:List[String] = time.getbeginTime().toString :: time.getendTime().toString :: List(other)
     var external = new Convertor("SA_TCP_DOWNLOAD_COUNT_AVERAGE_DSTIP")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }
  
  //TCP service 17
  //Compare the dowload and upload size (Use key to specific the type)
  def SC_TCP_DOWLOAD_UPLOAD_RATIO(time:Time,key:String,index:String,problem:String){
     var para:List[String] = obj.toString :: time.getbeginTime().toString :: time.getendTime().toString :: List(key,index,problem)
     var external = new Convertor("SC_TCP_DOWLOAD_UPLOAD_RATIO")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }

  //TCP service 18
  //Compare the dowload and upload size (Use key to specific the type)
  def SC_TCP_DOWLOAD_UPLOAD_ML(time:Time,key:String,index:String,problem:String){
     var para:List[String] = obj.toString :: time.getbeginTime().toString :: time.getendTime().toString :: List(key,index,problem)
     var external = new Convertor("SC_TCP_DOWLOAD_UPLOAD_ML")
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }*/

  /*
  //TCP service 
  //Statistic the upload TCP size of high risk IP
  def SH_TCP_UPLOAD_SIZE_SRCIP_RISK(key:String,begin:String,end:String){
     var external = new Convertor("SH_TCP_UPLOAD_SIZE_SRCIP_RISK",List(key,begin,end))
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }
  
  //TCP service 
  //Statistic the download TCP size of high risk IP
  def SH_TCP_DOWNLOAD_SIZE_SRCIP_RISK(key:String,begin:String,end:String){
     var external = new Convertor("SH_TCP_DOWNLOAD_SIZE_DSTIP_RISK",List(key,begin,end))
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }


  //Load the high risk ip from file source
  def SL_HIGH_RISK_IP_FILE(key:String,name:String){ 
     var external = new Convertor("SL_HIGH_RISK_IP_FILE",List(key,name))
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)  
  }
  
  //Load the high risk ip automatically
  def SL_HIGH_RISK_IP_AUTO(key:String,name:String){
     var external = new Convertor("SL_HIGH_RISK_IP_AUTO",List(key,name))
     var internal:List[String] = external.getInterface()
     send_to_queue(internal)
  }*/

}
