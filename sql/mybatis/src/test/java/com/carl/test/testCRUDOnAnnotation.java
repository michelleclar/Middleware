package com.carl.test;

import com.carl.mybatis.mapper.UserMapper;
import com.carl.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.carl.common.entity.User;
import org.carl.common.utils.tools.random.RandomNameAndTel;
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
public class testCRUDOnAnnotation {
    //单条
    @Test
    public void testCreat() {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
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
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.findByIdOnAnnotation(337648739647115264L).orElseThrow(
                () -> new RuntimeException("not found")
        );
        System.out.println(user);
    }

    @Test
    public void testUpdate(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Long userId = 337648739647115264L;
        Long dataVersion = mapper.findDataVersionById(userId).orElseThrow(
                () -> new RuntimeException("not fond")
        );
        mapper.updateById(userId,dataVersion,new User().setName(RandomNameAndTel.getChineseName())
                .setPhone(RandomNameAndTel.getTel()));
    }

    @Test
    public void testDelete(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Long userId = 337648739647115264L;
        mapper.deleteById(userId);
    }

    //批量

}
