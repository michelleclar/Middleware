package com.carl.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.Test;

import java.util.Arrays;

/**
 * @program: Middleware
 * @description: 测试消费者
 * @author: Mr.Carl
 **/
public class TestConsumer {

    @Test
    public void testConsumer() throws Exception {
        DmsConsumer consumer = new DmsConsumer();
        consumer.consume(Arrays.asList("topic-0"));
        try {
            for (int i = 0; i < 10; i++) {
                ConsumerRecords<Object, Object> records = consumer.poll(1000);
                System.out.println("the numbers of topic:" + records.count());
                for (ConsumerRecord<Object, Object> record : records) {
                    System.out.println(record.toString());
                }
            }
        } catch (Exception e) {
            //TODO: 异常处理
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}
