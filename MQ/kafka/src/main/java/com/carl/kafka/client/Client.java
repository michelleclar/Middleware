package com.carl.kafka.client;

import com.carl.common.utils.DmsConsumer;
import org.apache.jute.Index;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.protocol.types.Field;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @program: Middleware
 * @description:
 * @author: Mr.Carl
 **/
public class Client {
    public static class Builder {
        public static Client build() {
            return new Client();
        }
        public static Client build(String path) {
            return new Client(path);
        }
    }
    protected static final String CONFIG_CONSUMER_FILE_NAME = "getting-started.properties";
    AdminClient client;

    protected Client(String path) {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(path));
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        client = AdminClient.create(props);
    }

    protected Client() {
        Properties props = new Properties();
        try {
            props = loadFromClasspath(CONFIG_CONSUMER_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        client = AdminClient.create(props);
    }


    /**
     * get classloader from thread context if no classloader found in thread
     * context return the classloader which has loaded this class
     *
     * @return classloader
     */
    protected static ClassLoader getCurrentClassLoader() {
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        if (classLoader == null) {
            classLoader = DmsConsumer.class.getClassLoader();
        }
        return classLoader;
    }

    /**
     * 从classpath 加载配置信息
     *
     * @param configFileName 配置文件名称
     * @return 配置信息
     * @throws IOException
     */
    protected static Properties loadFromClasspath(String configFileName) throws IOException {
        ClassLoader classLoader = getCurrentClassLoader();
        Properties config = new Properties();

        List<URL> properties = new ArrayList<URL>();
        Enumeration<URL> propertyResources = classLoader
                .getResources(configFileName);
        while (propertyResources.hasMoreElements()) {
            properties.add(propertyResources.nextElement());
        }

        for (URL url : properties) {
            InputStream is = null;
            try {
                is = url.openStream();
                config.load(is);
            } finally {
                if (is != null) {
                    is.close();
                    is = null;
                }
            }
        }

        return config;
    }

    void createTopic(String topicName) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topicName, 3, (short) 1);
        CreateTopicsResult result =  client.createTopics(Collections.singleton(newTopic));
        result.all().get();

    }

    void createTopic(String topicName, int numPartitions, short replicationFactor) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        CreateTopicsResult result = client.createTopics(Collections.singleton(newTopic));
        result.all().get();
    }
}
