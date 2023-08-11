package elasticsearch.action.index;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.indices.IndexState;
import elasticsearch.client._ElasticsearchClient;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

/**
 * @program: Middleware
 * @description: 索引操作
 * @author: Mr.Carl
 **/
@Slf4j
public class Index {
    public static class Builder{
        public static Index build(){
            return new Index();
        }
    }

    /*
     * Carl
     * TODO: 创建索引
     */
    public void create(String indexName) throws IOException ,ElasticsearchException{
        try {
            _ElasticsearchClient.getInstance().indices().create(p ->
                    p.index(indexName)).acknowledged();
        } catch (IOException e) {
            log.warn("创建索引"+indexName+"失败\n"+e.getMessage());

        } catch (ElasticsearchException e) {
            log.warn("创建索引"+indexName+"失败\n"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /*
     * Carl
     * TODO: 获取索引
     */
    public Map<String, IndexState> getIndex(String indexName) throws IOException , ElasticsearchException {
        return _ElasticsearchClient.getInstance().indices().get(
                req -> req.index(indexName)
        ).result();
    }
    /**
     * Carl
     * TODO: 删除索引
     */
    public Boolean deleteIndex(String indexName) throws IOException {
        return  _ElasticsearchClient.getInstance().indices().delete(
                reqbuilder -> reqbuilder.index(indexName)
        ).acknowledged();
    }

}
