package com.carl.common.utils;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.checkerframework.checker.units.qual.C;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @program: Middleware
 * @description: 线程安全, 采用单例模式
 * @author: Mr.Carl
 **/
public class DmsProducer<K, V> {
    public static class Builder<K, V> {
        private static Map<Map<Class, Class>, DmsProducer> instances = new HashMap<>();

        static DmsProducer _producer;

        static <K, V> DmsProducer init(Class<K> k, Class<V> v) {
            Map<Class, Class> key = new HashMap();
            key.put(k, v);

            if (!instances.containsKey(key) && instances.get(key).producer == null)
                instances.put(key, new DmsProducer<K, V>());
            return instances.get(key);
        }

        static <K, V> DmsProducer init(Class<K> k, Class<V> v, String path) {
            Map<Class, Class> key = new HashMap();
            key.put(k, v);

            if (!instances.containsKey(key) && instances.get(key).producer == null)
                instances.put(key, new DmsProducer<K, V>(path));
            return instances.get(key);
        }

        public static <K, V> DmsProducer<K, V> build(Class<K> k, Class<V> v) {
            return init(k, v);
        }

        public static <K, V> DmsProducer<K, V> build(Class<K> k, Class<V> v, String path) {
            return init(k, v, path);
        }
    }

    //引入生产消息的配置信息，具体内容参考上文
    protected static final String CONFIG_PRODUCER_FILE_NAME = "producer.properties";

    protected Producer<K, V> producer;

    public DmsProducer(String path) {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(path));
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        producer = new KafkaProducer<K, V>(props);
    }

    public DmsProducer() {
        Properties props = new Properties();
        try {
            props = loadFromClasspath(CONFIG_PRODUCER_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        producer = new KafkaProducer<K, V>(props);
    }

    /**
     * 生产消息
     *
     * @param topic     topic对象
     * @param partition partition
     * @param key       消息key
     * @param data      消息数据
     */
    public void produce(String topic, Integer partition, K key, V data) {
        produce(topic, partition, key, data, null, (Callback) null);
    }

    /**
     * 生产消息
     *
     * @param topic     topic对象
     * @param partition partition
     * @param key       消息key
     * @param data      消息数据
     * @param timestamp timestamp
     */
    public void produce(String topic, Integer partition, K key, V data, Long timestamp) {
        produce(topic, partition, key, data, timestamp, (Callback) null);
    }

    /**
     * 生产消息
     *
     * @param topic     topic对象
     * @param partition partition
     * @param key       消息key
     * @param data      消息数据
     * @param callback  callback
     */
    public void produce(String topic, Integer partition, K key, V data, Callback callback) {
        produce(topic, partition, key, data, null, callback);
    }

    public void produce(String topic, V data) {
        produce(topic, null, null, data, null, (Callback) null);
    }

    /**
     * 生产消息
     *
     * @param topic     topic对象
     * @param partition partition
     * @param key       消息key
     * @param data      消息数据
     * @param timestamp timestamp
     * @param callback  callback
     */
    public void produce(String topic, Integer partition, K key, V data, Long timestamp, Callback callback) {
        ProducerRecord<K, V> kafkaRecord =
                timestamp == null ? new ProducerRecord<K, V>(topic, partition, key, data)
                        : new ProducerRecord<K, V>(topic, partition, timestamp, key, data);
        produce(kafkaRecord, callback);
    }

    public void produce(ProducerRecord<K, V> kafkaRecord) {
        produce(kafkaRecord, (Callback) null);
    }

    public void produce(ProducerRecord<K, V> kafkaRecord, Callback callback) {
        producer.send(kafkaRecord, callback);
    }

    public void close() {
        producer.close();
        producer = null;
    }

    /**
     * get classloader from thread context if no classloader found in thread
     * context return the classloader which has loaded this class
     *
     * @return classloader
     */
    public static ClassLoader getCurrentClassLoader() {
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        if (classLoader == null) {
            classLoader = DmsProducer.class.getClassLoader();
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
    public static Properties loadFromClasspath(String configFileName) throws IOException {
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
}
