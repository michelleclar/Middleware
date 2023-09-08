package elasticsearch.action.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.SimpleJsonpMapper;
import elasticsearch.action.index.TestIndex;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static elasticsearch.action.search.Search.esClient;

/**
 * @program: Middleware
 * @description: 测试搜索api
 * @author: Mr.Carl
 **/
public class TestSearch {
    static Search search = Search.Builder.build();

    @Test
    public void testSearchByJson() throws IOException {
        String query = """
                {
                  "query": {
                    "intervals" : {
                      "my_text" : {
                        "all_of" : {
                          "intervals" : [
                            { "match" : { "query" : "the" } },
                            { "any_of" : {
                              "intervals" : [
                                { "match" : { "query" : "big" } },
                                { "match" : { "query" : "big bad" } }
                              ] } },
                            { "match" : { "query" : "wolf" } }
                          ],
                          "max_gaps" : 0,
                          "ordered" : true
                        }
                      }
                    }
                  }
                }
                """;
        JsonpMapper mapper = SimpleJsonpMapper.INSTANCE_REJECT_UNKNOWN_FIELDS;
        InputStream input = this.getClass()
                .getResourceAsStream("some-index.json");
        CreateIndexRequest req = CreateIndexRequest.of(b -> b
                .index("some-index")
                //创建mapper映射
                .withJson(input)
        );
    }

    @Test
    public void testJsonData() {
        String query = """
                {
                  "query": {
                    "intervals" : {
                      "my_text" : {
                        "all_of" : {
                          "intervals" : [
                            { "match" : { "query" : "the" } },
                            { "any_of" : {
                              "intervals" : [
                                { "match" : { "query" : "big" } },
                                { "match" : { "query" : "big bad" } }
                              ] } },
                            { "match" : { "query" : "wolf" } }
                          ],
                          "max_gaps" : 0,
                          "ordered" : true
                        }
                      }
                    }
                  }
                }
                """;
        JsonData jsonData = JsonData.fromJson(query);
        System.out.println(jsonData);
    }

    @Test
    public void searchByJson() throws IOException {
        String searchText = "bike";
        SearchResponse<TestIndex.Product> response = esClient.search(s -> s
                        .index("products")
                        .query(q -> q
                                .match(t -> t
                                        .field("name")
                                        .query(searchText)
                                )
                        ),
                TestIndex.Product.class
        );
        QueryBuilders.match().field("name").query("bike");

    }
}
