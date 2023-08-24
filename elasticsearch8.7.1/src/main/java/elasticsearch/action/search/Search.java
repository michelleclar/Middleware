package elasticsearch.action.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.SearchTemplateResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.security.User;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import elasticsearch.client._ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import utils.exception.inter.CatchExceptions;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

/**
 * @program: Middleware
 * @description: 搜索响应
 * @author: Mr.Carl
 **/
@Slf4j
@CatchExceptions("类")
public class Search {
    //TODO: switch 实现json解析
    static ElasticsearchClient esClient = _ElasticsearchClient.getInstance();
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

    void test() throws IOException {
        // 创建script 模板
        _ElasticsearchClient.getInstance().putScript(r -> r
                .id("query-script")
                .script(s -> s
                        .lang("mustache")
                        .source("{\"query\":{\"match\":{\"{{field}}\":\"{{value}}\"}}}")
                ));
        SearchTemplateResponse<User> response = esClient.searchTemplate(r -> r
                        .index("some-index")
                        .id("query-script")
                        .params("field", JsonData.of("some-field"))
                        .params("value", JsonData.of("some-data")),
                User.class
        );

        List<Hit<User>> hits = response.hits().hits();
        for (Hit<User> hit: hits) {
            User user = hit.source();
            log.info("Found user " + user.fullName() + ", score " + hit.score());
        }
    }
}
