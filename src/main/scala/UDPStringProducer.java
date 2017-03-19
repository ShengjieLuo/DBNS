package shared_socket_producer;

/*
 * <dependency>
 *     <groupId>commons-cli</groupId>
 *     <artifactId>commons-cli</artifactId>
 *     <version>1.2</version>
 * </dependency>
 */

import org.apache.commons.cli.*;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPStringProducer {

    private static final int BUFFER_SIZE = 1024;



    public static void main(final String[] args) {

        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        HelpFormatter formatter = new HelpFormatter();

        options.addOption("p", "port", true, "UDP port to listen on.");
        options.addOption("b", "broker", true, "Kafka broker.");
        options.addOption("t", "topic", true, "Topic of message being generated.");
        options.addOption("n", "n-threads", true, "Num of threads to run.");

        try {

            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption('p') && cmd.hasOption('b') && cmd.hasOption('t')) {

                // Parse cmd line
                int port = Integer.parseInt(cmd.getOptionValue('p'));
                String broker = cmd.getOptionValue('b');
                String topic = cmd.getOptionValue('t');
                int nThreads = Integer.parseInt(cmd.getOptionValue('n', "8"));

                // socket init
                DatagramSocket socket = new DatagramSocket(port);

                // thread pool
                ExecutorService threadPool = Executors.newFixedThreadPool(nThreads);
                for (int i = 0; i < nThreads; i++) {
                    threadPool.execute(new MsgProducer(socket, broker, topic));
                }

            } else {
                throw new ParseException("");
            }

        } catch (ParseException | NumberFormatException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("UDPStringProducer", options);
            System.exit(1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }

}
