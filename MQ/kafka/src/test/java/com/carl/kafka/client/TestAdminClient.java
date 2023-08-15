package com.carl.kafka.client;

import com.carl.kafka.client.Client;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @program: Middleware
 * @description: 测试kafka 通道创建
 * @author: Mr.Carl
 **/
public class TestAdminClient {
    static Client client = Client.Builder.build();

    @Test
    public void testConnectKafka() throws Exception {
        // 创建kafka连接属性
        Properties props = new Properties();
        props.put("bootstrap.servers", "101.43.4.193:9092");

        // 创建连接
        AdminClient client = AdminClient.create(props);

        // 列出topics测试
        client.listTopics(new ListTopicsOptions().listInternal(true)).names().get(100, TimeUnit.SECONDS).forEach(System.out::println);

        // 关闭连接
        client.close();
    }

    @Test
    public void testZookeeper() throws Exception {
        // 创建zookeeper连接字符串
        String connString = "101.43.4.193:2181";

        // 创建连接
        ZooKeeper zookeeper = new ZooKeeper(connString, 10000, null);

        // 打印详情
        System.out.println(zookeeper.getState());

        // 关闭连接
        zookeeper.close();
    }
    @Test
    public void testCreatTopic() {
        client.createTopic("topic-0");
    }
}
