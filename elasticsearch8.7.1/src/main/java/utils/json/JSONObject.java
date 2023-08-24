package utils.json;

/**
 * @program: Middleware
 * @description:
 * @author: Mr.Carl
 **/
public class JSONObject extends com.alibaba.fastjson.JSONObject {


    public String getFirstKey(){
        String firstKey = getInnerMap().keySet().iterator().next();
        return firstKey;
    }
}
