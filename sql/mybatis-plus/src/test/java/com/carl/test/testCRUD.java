package com.carl.test;


import generator.mapper.UserMapper;
import generator.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.carl.common.entity.User;
import org.carl.common.utils.tools.random.RandomNameAndTel;
import org.carl.common.utils.tools.security.jwt.enums.AuthRole;
import org.carl.common.utils.tools.tool;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @program: Middleware
 * @description:
 * @author: Mr.Carl
 **/
public class testCRUD {
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
        mapper.insert(user);
    }

    @Test
    public void testRead(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Long id = 338357226182447104L;
        User user = mapper.selectByPrimaryKey(id);
        System.out.println(user);
    }

    @Test
    public void testUpdate(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Long id = 338357226182447104L;
        User user = new User()
                .setName(RandomNameAndTel.getChineseName())
                .setUsername(RandomNameAndTel.getChineseName())
                .setPhone(RandomNameAndTel.getTel())
                .setId(id)
                .setRole(AuthRole.USER)
                .setPassword(tool.getEncode("abc123"))
                .setIsDisable(0)
                .setDataVersion(0L)
                .setCreatedAt(LocalDateTime.now());
        mapper.updateByPrimaryKey(user);
    }

    @Test
    public void testDelete(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Long userId = 337648739647115264L;
        mapper.deleteByPrimaryKey(userId);
    }

}
