### docker 运行

```sh
docker network create elastic

docker run \
      --name elasticsearch \
      --net elastic \
      -p 9200:9200 \
      -e discovery.type=single-node \
      -e ES_JAVA_OPTS="-Xms1g -Xmx1g"\
      -e xpack.security.enabled=false \
      -v es-data:/usr/share/elasticsearch/data \
      -v es-plugins:/usr/share/elasticsearch/plugins \
      -it docker.elastic.co/elasticsearch/elasticsearch:8.7.1
```

