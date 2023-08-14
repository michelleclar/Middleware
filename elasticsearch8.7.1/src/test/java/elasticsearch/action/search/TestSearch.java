package elasticsearch.action.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import elasticsearch.action.index.Index;
import org.junit.Test;

/**
 * @program: Middleware
 * @description: 测试搜索api
 * @author: Mr.Carl
 **/
public class TestSearch {
    static Search search = Search.Builder.build();

    @Test
    void testSearchByJson(){
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

    }
}
