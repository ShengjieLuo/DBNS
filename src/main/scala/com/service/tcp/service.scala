pacakge com.service.tcp

import com.topo.External

class tcpservice(){

  val brokers = "spark-master:9092"
  val topic = "internal" 

  val producer = {
    val props = new HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](props) 
  }

  def send_to_queue(content){
    val message = new ProducerRecord[String, String](topic, null, content)
    producer.send(message)
  }

  //Statistic the upload TCP size of specific IP
  def SS_TCP_UPLOAD_SIZE_SRCIP(key:String,begin:String,end:String){
     var external = new External(type="SS_TCP_UPLOAD_SIZE_SRCIP",parameter=List(key,begin,end))
     var internal:String = service.getInterface()
     send_to_queue(internal)  
  }

  //Statistic the upload TCP size of all IP
  def SA_TCP_UPLOAD_SIZE_SRCIP(begin:String,end:String){ 
     var external = new External(type="SA_TCP_UPLOAD_SIZE_SRCIP",parameter=List(begin,end))
     var internal:String = service.getInterface()
     send_to_queue(internal)  
  }
 
  //Statistic the upload TCP size of high risk IP
  def SH_TCP_UPLOAD_SIZE_SRCIP_RISK(key:String,begin:String,end:String){
     var external = new External(type="SH_TCP_UPLOAD_SIZE_SRCIP_RISK",parameter=List(key,begin,end))
     var internal:String = service.getInterface()
     send_to_queue(internal)
  }

  //Statistic the upload TCP size totally
  def SA_TCP_UPLOAD_SIZE(begin:String,end:String){ 
     var external = new External(type="SA_TCP_UPLOAD_SIZE",parameter=List(begin,end))
     var internal:String = service.getInterface()
     send_to_queue(internal)  
  }

  //Statistic the download TCP size of specific IP
  def SS_TCP_DOWNLOAD_SIZE_DSTIP(key:String,begin:String,end:String){
     var external = new External(type="SS_TCP_DOWNLOAD_SIZE_DSTIP",parameter=List(key,begin,end))
     var internal:String = service.getInterface()
     send_to_queue(internal)  
  }

  //Statistic the download TCP size of all IP
  def SA_TCP_DOWNLOAD_SIZE_DSTIP(begin:String,end:String){ 
     var external = new External(type="SA_TCP_DOWNLOAD_SIZE_DSTIP",parameter=List(begin,end))
     var internal:String = service.getInterface()
     send_to_queue(internal)  
  }
  
  //Statistic the download TCP size of high risk IP
  def SH_TCP_DOWNLOAD_SIZE_SRCIP_RISK(key:String,begin:String,end:String){
     var external = new External(type="SH_TCP_DOWNLOAD_SIZE_DSTIP_RISK",parameter=List(key,begin,end))
     var internal:String = service.getInterface()
     send_to_queue(internal)
  }

  //Statistic the upload TCP size totally
  def SA_TCP_DOWNLOAD_SIZE(begin:String,end:String){ 
     var external = new External(type="SA_TCP_DOWNLOAD_SIZE",parameter=List(begin,end))
     var internal:String = service.getInterface()
     send_to_queue(internal)  
  }

  //Load the high risk ip from file source
  def SL_HIGH_RISK_IP_FILE(key:String,name:String){ 
     var external = new External(type="SL_HIGH_RISK_IP_FILE",parameter=List(begin,end))
     var internal:String = service.getInterface()
     send_to_queue(internal)  
  }
  
  //Load the high risk ip automatically
  def SL_HIGH_RISK_IP_AUTO(key:String,name:String){
     var external = new External(type="SL_HIGH_RISK_IP_AUTO",parameter=List(key,name))
     var internal:String = service.getInterface()
     send_to_queue(internal)
  }

  //Compare the dowload and upload size (Use key to specific the type)
  def SC_TCP_DOWLOAD_UPLOAD_RATIO(key:String,index:String){
     var external = new External(type="SC_TCP_DOWLOAD_UPLOAD_RATIO",parameter=List(key,index))
     var internal:String = service.getInterface()
     send_to_queue(internal)
  }

  //Compare the dowload and upload size (Use key to specific the type)
  def SC_TCP_DOWLOAD_UPLOAD_ML(key:String,index:String){
     var external = new External(type="SC_TCP_DOWLOAD_UPLOAD_ML",parameter=List(key,index))
     var internal:String = service.getInterface()
     send_to_queue(internal)
  }

}
