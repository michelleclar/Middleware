import com.gen.Tables;
import org.carl.common.utils.tools.Tool;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserCrudTest {
    String jdbcUrl = "jdbc:mysql://172.18.0.2:3306/study";
    String username = "root";
    String password = "root";
    DSLContext dslContext = DSL.using(jdbcUrl, username, password);

    // 在测试之前，可以添加一些初始数据
    @BeforeEach
    public void setup() {
        dslContext.insertInto(Tables.USER)
                .set(Tables.USER.NAME, "John")
                .set(Tables.USER.PHONE, Tool.getTel())
                .execute();
    }

    @Test
    public void testCreateReadUpdateDelete() {
        String tel = Tool.getTel();
        // 创建用户
        dslContext.insertInto(Tables.USER)
                .set(Tables.USER.NAME, "Alice")
                .set(Tables.USER.PHONE, tel)
                .execute();

        // 读取用户
        Result<Record> result = dslContext.select()
                .from(Tables.USER)
                .where(Tables.USER.NAME.eq("Alice"))
                .fetch();

        assertNotNull(result);
        assertEquals(1, result.size());

        Record record = result.get(0);
        assertEquals("Alice", record.getValue(Tables.USER.NAME));
        assertEquals(tel, record.getValue(Tables.USER.PHONE));
        tel = Tool.getTel();
        // 更新用户
        dslContext.update(Tables.USER)
                .set(Tables.USER.PHONE, tel)
                .where(Tables.USER.NAME.eq("Alice"))
                .execute();

        // 再次读取用户
        Result<Record> updatedResult = dslContext.select()
                .from(Tables.USER)
                .where(Tables.USER.NAME.eq("Alice"))
                .fetch();

        assertNotNull(updatedResult);
        assertEquals(1, updatedResult.size());

        Record updatedRecord = updatedResult.get(0);
        assertEquals("Alice", updatedRecord.getValue(Tables.USER.NAME));
        assertEquals(tel, updatedRecord.getValue(Tables.USER.PHONE));

        // 删除用户
        dslContext.delete(Tables.USER)
                .where(Tables.USER.NAME.eq("Alice"))
                .execute();

        // 验证用户已删除
        Result<Record> deletedResult = dslContext.select()
                .from(Tables.USER)
                .where(Tables.USER.NAME.eq("Alice"))
                .fetch();

        assertNotNull(deletedResult);
        assertEquals(0, deletedResult.size());
    }
}
