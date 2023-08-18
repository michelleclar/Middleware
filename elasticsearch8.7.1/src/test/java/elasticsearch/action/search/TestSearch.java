package elasticsearch.action.search;

import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jsonb.JsonbJsonpMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.json.Json;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

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
        InputStream input = this.getClass()
                .getResourceAsStream("some-index.json");
        CreateIndexRequest req = CreateIndexRequest.of(b -> b
                .index("some-index")
                //创建mapper映射
                .withJson(input)
        );
    }
}
