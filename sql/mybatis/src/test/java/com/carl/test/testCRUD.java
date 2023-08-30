package com.carl.test;

import com.carl.mybatis.mapper.UserMapper;
import com.carl.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.carl.common.entity.User;
import org.carl.common.utils.tools.security.jwt.enums.AuthRole;
import org.junit.jupiter.api.Test;
import org.carl.common.utils.tools.tool;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @program: Middleware
 * @description:
 * @author: Mr.Carl
 **/
public class testCRUD {



    @Test
    public void testCreat() throws IOException {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
//        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        User user = new User()
                .setName(RandomNameAndTel.getChineseName())
                .setUsername(RandomNameAndTel.getChineseName())
                .setPhone(RandomNameAndTel.getTel())
                .setId(tool.getSnowFlakeId())
                .setRole(AuthRole.USER)
                .setPassword(tool.getEncode("123456"))
                .setIsDisable(0)
                .setDataVersion(0L)
                .setCreatedAt(LocalDateTime.now());
        mapper.saveOnAnnotation(user);
    }

    @Test
    public void testRead(){

    }

    @Test
    public void testUpdate(){}

    @Test
    public void testDelete(){}
}
