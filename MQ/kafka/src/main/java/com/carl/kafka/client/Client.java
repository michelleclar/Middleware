package com.carl.kafka.client;

import com.carl.common.utils.DmsConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;

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
@Slf4j
public class Client {
    public static class Builder {
        static Client _client;
        static Client  init(){
            if (_client == null || _client.client == null){
                _client = new Client();
            }
            return _client;
        }

        static Client  init(String path){
            if (_client == null || _client.client == null){
                _client = new Client(path);
            }
            return _client;
        }
        public static Client build() {
            return init();
        }

        public static Client build(String path) {
            return init(path);
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

    public void createTopic(String topicName) {
        NewTopic newTopic = new NewTopic(topicName, 3, (short) 1);
        CreateTopicsResult result = client.createTopics(Collections.singleton(newTopic));
        execute(result);
    }

    void execute(CreateTopicsResult r) {
        try {
            r.all().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TopicExistsException e) {
            //此异常可不进行处理
            log.warn(e.toString());
//            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public void createTopic(String topicName, int numPartitions, short replicationFactor) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        CreateTopicsResult result = client.createTopics(Collections.singleton(newTopic));
        result.all().get();
    }

    public void close() {
        client.close();
        client = null;
    }
}
