package elasticsearch.action.search;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


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
        String s = jsonObject.toString();
        jsonObject.forEach((k,v)->
                System.out.println("k:"+k.toString()+" v:"+v.toString()));
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
