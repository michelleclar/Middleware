package elasticsearch.action.search;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;


/**
 * @program: Middleware
 * @description:
 * @author: Mr.Carl
 **/
public class SearchQuery {

    public static class Builder {
        public static SearchQuery build() {
            return new SearchQuery();
        }
    }

    void buildQueryByJson(String json){
        JSONObject jsonObject = JSON.parseObject(json);
        String firstKey = getFirstKey(jsonObject.getInnerMap());
        System.out.println(firstKey);
    }

    public String getFirstKey(Map<String, Object> map){
        String firstKey = map.keySet().iterator().next();
        return firstKey;
    }


    public static void main(String[] args) {
        SearchQuery build = Builder.build();
        build.buildQueryByJson("{\"query\":{\"match\":{\"name\":\"carl\"}}}");
    }
}
