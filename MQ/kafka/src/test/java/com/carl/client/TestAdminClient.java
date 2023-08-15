package com.carl.client;

import com.carl.kafka.client.Client;
import org.junit.Test;

/**
 * @program: Middleware
 * @description: 测试kafka 通道创建
 * @author: Mr.Carl
 **/
public class TestAdminClient {
    static Client client = Client.Builder.build();

    @Test
    public void testCreatTopic() {
        client.createTopic("topic-1");
        client.close();
        client.createTopic("topic-0");
    }
}
