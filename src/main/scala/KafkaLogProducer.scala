import java.util.HashMap
import org.apache.kafka.clients.producer.{ProducerConfig, KafkaProducer, ProducerRecord}
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf
import scala.io.Source
import java.util.concurrent.{Executors, ExecutorService}

object LogProducer {
  
  class Message(props:HashMap[String,Object],source:String,topic:String) extends Runnable{
    def run(){
      val producer = new KafkaProducer[String, String](props)// Send some messages
      val lines = Source.fromFile(source).getLines.toList
      for (line<-lines){
        println(topic+": "+line)
        val message = new ProducerRecord[String, String](topic, null, line)
        producer.send(message)
        Thread.sleep(10)
      }
    }
  }

  def main(args: Array[String]) {
    val threadPool:ExecutorService=Executors.newFixedThreadPool(4)
    //val sourcepool = Array("/root/text/http_response.txt","/root/text/http_request.txt","/root/text/dns_response.txt","/root/text/dns_request.txt")
    //val topicpool = Array("httpResponse","httpRequest","dnsResponse","dnsRequest")
    val sourcepool = Array("/root/text/http_request.txt")
    val topicpool = Array("httpRequest")
    val Array(brokers, topic) = args
    val props = new HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    for (i <- 0 to 0){
      threadPool.execute(new Message(props,sourcepool(i),topicpool(i)))
    }
  }
}
