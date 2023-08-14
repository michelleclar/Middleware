package elasticsearch.action.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.util.ObjectBuilder;
import elasticsearch.action.index.Index;
import elasticsearch.client._ElasticsearchClient;
import utils.exception.inter.CatchExceptions;

import java.io.IOException;
import java.util.function.Function;

/**
 * @program: Middleware
 * @description: 搜索响应
 * @author: Mr.Carl
 **/
@CatchExceptions("类")
public class Search {
    public static class Builder {
        public static Search build() {
            return new Search();
        }
    }
    <O> void create(co.elastic.clients.elasticsearch.core.SearchRequest request, Class<O> clazz) {
        SearchResponse<O> search = null;
        try {
            search = _ElasticsearchClient.getInstance().search(request,
                    clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Hit<O> hit : search.hits().hits()) {
            System.out.println(hit.source());
        }
    }

   <T> void run(Function<SearchRequest.Builder, ObjectBuilder<SearchRequest>> fn, Class<T> clazz) throws IOException {
        _ElasticsearchClient.getInstance()
                .search(fn,
                        clazz);
    }

    void test(){
        SearchRequest.Builder builder = new SearchRequest.Builder();

    }
}
