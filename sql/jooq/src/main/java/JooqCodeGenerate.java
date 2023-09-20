import com.gen.Tables;
import com.gen.tables.User;
import com.gen.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.jooq.codegen.GenerationTool;
import org.jooq.impl.DSL;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generate;
import org.jooq.meta.jaxb.Target;

import java.util.Properties;

public class JooqCodeGenerate {
    public static void main(String[] args) throws Exception {
        // 数据库连接信息
        String jdbcUrl = "jdbc:mysql://172.18.0.2:3306/study";
        String username = "root";
        String password = "root";
        // 生成配置
        Configuration configuration = new Configuration()
                .withJdbc(new org.jooq.meta.jaxb.Jdbc()
                        .withDriver("com.mysql.cj.jdbc.Driver")
                        .withUrl(jdbcUrl)
                        .withUser(username)
                        .withPassword(password))
                .withGenerator(new org.jooq.meta.jaxb.Generator()
                        .withDatabase(new Database()
                                .withName("org.jooq.meta.mysql.MySQLDatabase")
                                .withInputSchema("study") // 数据库模式
                                .withIncludes(".*") // 包含生成的表
//                                .withExcludes("")
                        ) // 排除不生成的表
                        .withGenerate(new Generate()
                                .withPojos(true) // 生成POJO类
                                .withDaos(true)) // 生成DAO类
                        .withTarget(new Target()
                                .withPackageName("com.gen") // 生成类的包名
                                .withDirectory("sql/jooq/src/main/java"))); // 生成类的输出目录

        // 执行代码生成
        GenerationTool.generate(configuration);
    }
}
