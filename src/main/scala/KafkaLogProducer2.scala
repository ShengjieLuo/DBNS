import java.util.HashMap
import org.apache.kafka.clients.producer.{ProducerConfig, KafkaProducer, ProducerRecord}
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf
import scala.io.Source
import java.util.concurrent.{Executors, ExecutorService}

object LogProducer_2 {
  
  class Message(props:HashMap[String,Object],source:String,topic:String) extends Runnable{
    def run(){
      val producer = new KafkaProducer[String, String](props)// Send some messages
      val lines = Source.fromFile(source,"iso-8859-1").getLines.toList
      for (line<-lines){
        //println(topic+": "+line)
        val message = new ProducerRecord[String, String](topic, null, line)
        producer.send(message)
        Thread.sleep(100)
      }
    }
  }

  def main(args: Array[String]) {
    val threadPool:ExecutorService=Executors.newFixedThreadPool(4)
    val sourcepool = Array("/usr/local/DBNS/sample/netflow.txt","/usr/local/DBNS/sample/natlog.txt","/usr/local/DBNS/sample/syslog.txt")
    val topicpool = Array("netflow","natlog","syslog")
    val Array(brokers, topic) = args
    val props = new HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    for (i <- 0 to 2){
      threadPool.execute(new Message(props,sourcepool(i),topicpool(i)))
    }
  }
}
