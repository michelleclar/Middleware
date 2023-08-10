package elasticsearch.action.search;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import elasticsearch.client._ElasticsearchClient;

import java.io.IOException;

/**
 * @program: Middleware
 * @description: 搜索响应
 * @author: Mr.Carl
 **/
public class Search {
    <O> void create(co.elastic.clients.elasticsearch.core.SearchRequest request, Class<O> clazz){
        SearchResponse<O> search = null;
        try {
            search = _ElasticsearchClient.getInstance().search(request,
                    clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Hit<O> hit: search.hits().hits()) {
            System.out.println(hit.source());
        }
    }
}

class User{
    String name;
    int age;
}
