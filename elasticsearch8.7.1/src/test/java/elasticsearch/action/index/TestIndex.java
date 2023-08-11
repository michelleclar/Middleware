package elasticsearch.action.index;


import co.elastic.clients.elasticsearch.indices.IndexState;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @program: Middleware
 * @description: 测试索引Api
 * @author: Mr.Carl
 **/
public class TestIndex {

    static Index index = Index.Builder.build();
    @Test
    public void testBuild() throws IOException {
        Map<String, IndexState> person = index.getIndex("person");
        System.out.println(person);
    }

    @Test
    public void testCreate() throws IOException {
        index.create("person");
    }
}
