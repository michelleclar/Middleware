```yaml
version: "3"
services:
  zookeeper:
    image: 'bitnami/zookeeper:latest'
    ports:
      - '2181:2181'
    environment:
      # 匿名登录--必须开启
      - ALLOW_ANONYMOUS_LOGIN=yes
    volumes:
      - ./zookeeper:/bitnami/zookeeper
  # 该镜像具体配置参考 https://github.com/bitnami/bitnami-docker-kafka/blob/master/README.md
  kafka:
    image: 'bitnami/kafka:2.8.0'
    ports:
      - '9092:9092'
      - '9999:9999'
    environment:
      - KAFKA_BROKER_ID=1
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092
      # 客户端访问地址，更换成自己的
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://123.57.236.125:9092
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      # 允许使用PLAINTEXT协议(镜像中默认为关闭,需要手动开启)
      - ALLOW_PLAINTEXT_LISTENER=yes
      # 关闭自动创建 topic 功能
      - KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE=false
      # 全局消息过期时间 6 小时(测试时可以设置短一点)
      - KAFKA_CFG_LOG_RETENTION_HOURS=6
      # 开启JMX监控
      - JMX_PORT=9999
    volumes:
      - ./kafka:/bitnami/kafka
    depends_on:
      - zookeeper
  # Web 管理界面 另外也可以用exporter+prometheus+grafana的方式来监控 https://github.com/danielqsj/kafka_exporter
  kafka_manager:
    image: 'hlebalbau/kafka-manager:latest'
    ports:
      - "9000:9000"
    environment:
      ZK_HOSTS: "zookeeper:2181"
      APPLICATION_SECRET: letmein
    depends_on:
      - zookeeper
      - kafka

```

```yaml
version: "3"
services:
  zookeeper:
    image: 'bitnami/zookeeper:latest'
    ports:
      - '2181:2181'
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
    volumes:
      - ./zookeeper:/bitnami/zookeeper
  kafka:
    image: 'bitnami/kafka:latest'
    ports:
      - '9092:9092'
      - '9999:9999'
    environment:
      - KAFKA_BROKER_ID=1
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://10.0.4.7:9092
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
      - KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE=false
      - KAFKA_CFG_LOG_RETENTION_HOURS=6
      - JMX_PORT=9999
    volumes:
      - ./kafka:/bitnami/kafka
    depends_on:
      - zookeeper
  kafka_manager:
    image: 'hlebalbau/kafka-manager:latest'
    ports:
      - "9000:9000"
    environment:
      ZK_HOSTS: "zookeeper:2181"
      APPLICATION_SECRET: letmein
    depends_on:
      - zookeeper
      - kafka
```



```yaml
version: '3.7'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    volumes:
      - ./data:/data
    ports:
      - 2182:2181
       
  kafka9094:
    image: wurstmeister/kafka
    ports:
      - 9092:9092
    environment:
      KAFKA_BROKER_ID: 0
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://192.168.1.60:9092
      KAFKA_CREATE_TOPICS: "kafeidou:2:0"   #kafka启动后初始化一个有2个partition(分区)0个副本名叫kafeidou的topic 
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
    volumes:
      - ./kafka-logs:/kafka
    depends_on:
      - zookeeper
```