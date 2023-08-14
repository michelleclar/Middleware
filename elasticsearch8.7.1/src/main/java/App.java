import elasticsearch.action.index.Index;

/**
 * @program: Middleware
 * @description: 测试
 * @author: Mr.Carl
 **/
public class App {
    public static void main(String[] args) throws Exception{
        System.out.println("person");
        Index.Builder.build().create("person");
    }
}
