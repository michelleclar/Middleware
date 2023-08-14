package elasticsearch.action.index;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.indices.IndexState;
import elasticsearch.client._ElasticsearchClient;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import utils.exception.inter.CatchException;

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
    public static class Builder {
        public static Index build() {
            return new Index();
        }
    }

    /*
     * Carl
     * TODO: 创建索引
     */
    @CatchException("方法")
    public void create(String indexName) throws IOException, ElasticsearchException {
        try {
            _ElasticsearchClient.getInstance().indices().create(p ->
                    p.index(indexName)).acknowledged();
        } catch (IOException e) {
            log.warn("创建索引" + indexName + "失败:" + e.getMessage() + "\nmethod{},params:[{}]", this.getClass().getSimpleName() + "." + new Object() {
            }.getClass().getEnclosingMethod().getName(), indexName);
            throw e;
        } catch (ElasticsearchException e) {
            log.warn("创建索引" + indexName + "失败:" + e.getMessage() + "\nmethod{},params:[{}]", this.getClass().getSimpleName() + "." + new Object() {
            }.getClass().getEnclosingMethod().getName(), indexName);
            throw e;
        }
        log.info("创建索引" + indexName + "成功");
    }

    /*
     * Carl
     * TODO: 获取索引
     */
    public Map<String, IndexState> getIndex(String indexName) throws IOException, ElasticsearchException {
        Map<String, IndexState> result;
        try {
            result = _ElasticsearchClient.getInstance().indices().get(
                    req -> req.index(indexName)
            ).result();
        } catch (IOException e) {
            log.warn("获取索引" + indexName + "失败:" + e.getMessage() + "\nmethod{},params:[{}]", this.getClass().getSimpleName() + "." + new Object() {
            }.getClass().getEnclosingMethod().getName(), indexName);
            throw e;
        } catch (ElasticsearchException e) {
            log.warn("获取索引" + indexName + "失败:" + e.getMessage() + "\nmethod{},params:[{}]", this.getClass().getSimpleName() + "." + new Object() {
            }.getClass().getEnclosingMethod().getName(), indexName);
            throw e;
        }
        return result;
    }

    /**
     * Carl
     * TODO: 删除索引
     */
    public void deleteIndex(String indexName) throws IOException {
        try {
            _ElasticsearchClient.getInstance().indices().delete(
                    reqbuilder -> reqbuilder.index(indexName)
            ).acknowledged();
        } catch (IOException e) {
            log.warn("删除索引" + indexName + "失败:" + e.getMessage());
            throw e;
        } catch (ElasticsearchException e) {
            log.warn("删除索引" + indexName + "失败:" + e.getMessage());
            throw e;
        }
    }

}
