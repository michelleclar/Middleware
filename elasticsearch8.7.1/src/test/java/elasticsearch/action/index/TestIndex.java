package elasticsearch.action.index;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexState;
import elasticsearch.client._ElasticsearchClient;
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
    public void testCreate() throws IOException {
        index.create("person");
    }

    @Test
    public void testGet() throws IOException {
        Map<String, IndexState> person = index.getIndex("person");
        System.out.println(person);
    }


    @Test
    public void testDelete() throws IOException {
        index.deleteIndex("person");
    }

    @Test
    public void testIndexDocByOne() throws IOException {
        Product product = new Product("bk-1", "City bike", 123.0);

        IndexRequest.Builder<Product> indexReqBuilder = new IndexRequest.Builder<>();
        indexReqBuilder.index("product");
        indexReqBuilder.id(product.getSku());
        indexReqBuilder.document(product);
        ElasticsearchClient esClient = _ElasticsearchClient.getInstance();
        IndexResponse response = esClient.index(indexReqBuilder.build());
        System.out.println("Indexed with version " + response.version());
    }

    class Product {
        String sku;
        String city;
        double price;

        public Product(String sku, String city, double price) {
            this.sku = sku;
            this.city = city;
            this.price = price;
        }

        String getSku() {
            return this.sku;
        }

        String getCity() {
            return this.city;
        }

        double getPrice() {
            return this.price;
        }
    }
}
