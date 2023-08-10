package elasticsearch;

import elasticsearch.client._ElasticsearchAsyncClient;
import elasticsearch.client._ElasticsearchClient;
import elasticsearch.common.ClientMode;

/**
 * @program: Middleware
 * @description: 客户端构建工厂
 * @author: Mr.Carl
 **/
public class ClientFactory {
    Object getClient(ClientMode mode){
        switch (mode){
            case AsyncClient -> {
                return _ElasticsearchAsyncClient.getInstance();
            }
            default -> {
                return _ElasticsearchClient.getInstance();
            }
        }
    }
}
