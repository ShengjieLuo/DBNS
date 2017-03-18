package shared_socket_producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

class MsgProducer implements Runnable {

    private static final int BUFFER_SIZE = 1024;

    private KafkaProducer<String, String> producer;
    private String topic;
    private DatagramSocket socket;
    private byte[] buffer;
    private DatagramPacket packet;

    MsgProducer(DatagramSocket socket,
                String broker,
                String topic) {

        this.socket = socket;
        this.topic = topic;

        buffer = new byte[BUFFER_SIZE];
        packet = new DatagramPacket(buffer, BUFFER_SIZE);

        // kafka init
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broker);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);

    }

    @Override
    public void run() {

        while (true) {
            try {
                socket.receive(packet);
                String sentence = new String(packet.getData(), 0, packet.getLength());
                producer.send(new ProducerRecord<>(topic, sentence));
            } catch (IOException ignored) {
                ;
            }
        }

    }
} // class MsgProducer
