package elasticsearch.client;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

/**
 * @program: Middleware
 * @description: 封装Elasticsearch的客户端 为了可以封装其内部方法
 * @author: Mr.Carl
 **/
//TODO: check 线程池自定义属性待改 各属性不使用硬编码
@Slf4j
public class _ElasticsearchAsyncClient extends ElasticsearchAsyncClient {
    /*懒加载的两种书写方式*/
    //方式1
    private static class Inner {
        _ElasticsearchAsyncClient create(){
            String serverUrl = "http://101.43.4.193:9200";
            restClient = RestClient
                    .builder(HttpHost.create(serverUrl))
                    .build();
            transport = new RestClientTransport(
                    restClient, new JacksonJsonpMapper());
            return new _ElasticsearchAsyncClient(transport);
        }
        public static _ElasticsearchAsyncClient init() {
            Function<Inner, _ElasticsearchAsyncClient> create = Inner::create;
            return create.apply(new Inner());
        }

        public static void close(){
            try {
                if (Objects.nonNull(restClient))
                    restClient.close();
                if (Objects.nonNull(transport))
                    transport.close();
            } catch (IOException e) {
                //TODO es需要一个专属的图标
                log.warn(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        static RestClient restClient;
        static ElasticsearchTransport transport;
    }

    public static _ElasticsearchAsyncClient getInstance() {
        return Inner.init();
    }
    public _ElasticsearchAsyncClient(ElasticsearchTransport transport) {
        super(transport);
        log.info("\uD83D\uDD87\uD83D\uDD87\uD83D\uDD87:es客户端连接成功");
    }

    public static void close(){
        Inner.close();
    }

    /*=================================*/


    public void search(SearchRequest request){

    }
}
