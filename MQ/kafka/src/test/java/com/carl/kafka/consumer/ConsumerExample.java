package com.carl.kafka.consumer;

import com.carl.kafka.producer.ProducerExample;
import org.apache.kafka.clients.consumer.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @program: Middleware
 * @description: 消费者
 * @author: Mr.Carl
 **/
public class ConsumerExample {
    public static void main(final String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Please provide the configuration file path as a command line argument");
            System.exit(1);
        }

        final String topic = "purchases";

        // Load consumer configuration settings from a local file
        // Reusing the loadConfig method from the com.carl.client.ProducerExample class
        final Properties props = ProducerExample.loadConfig(args[0]);
        var o = props.get("bootstrap.servers");
        o = "101.43.4.193:9200";
        System.out.println(o);
        // Add additional properties.
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-java-getting-started");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try (final Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Arrays.asList(topic));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    String value = record.value();
                    System.out.println(
                            String.format("Consumed event from topic %s: key = %-10s value = %s", topic, key, value));
                }
            }
        }
    }

}
