import java.util.HashMap
import org.apache.kafka.clients.producer.{ProducerConfig, KafkaProducer, ProducerRecord}
import java.util.concurrent.{Executors, ExecutorService}
import java.net.{DatagramPacket,DatagramSocket,InetAddress}

object LogUDPProducer{
  
  class Message(producer:KafkaProducer[String,String],topic:String,packet:DatagramPacket) extends Runnable{
    def run(){
      val sentence:String = new String(packet.getData(),0,packet.getLength()-1)
      //println(Thread.currentThread().getName())
      //println(topic+": "+sentence)
      val message = new ProducerRecord[String, String](topic, null, sentence)
      producer.send(message)
    }
  }

  def main(args: Array[String]) {
    val Array(broker,sleep,topic,port) = args
    val threadPool:ExecutorService=Executors.newCachedThreadPool()

    /*Defualt Configuration
    val topic:String = "test"
    val broker:String = "spark-master:9092"
    val sleep:Int = 1
    val port:Int = 9999*/

    val portNum:Int = port.toInt
    val sleepNum:Int = sleep.toInt
    val props = new HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broker)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)// Send some messages
    val serverSocket:DatagramSocket = new DatagramSocket(portNum)
    while(true)
      {
          val receiveData = new Array[Byte](1024)
          val receivePacket:DatagramPacket = new DatagramPacket(receiveData, receiveData.length)
          serverSocket.receive(receivePacket)
          threadPool.execute(new Message(producer,topic,receivePacket))                
      }
  }
}
