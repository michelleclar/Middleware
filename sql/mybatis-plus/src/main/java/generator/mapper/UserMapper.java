package generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.carl.common.entity.User;


/**
* @author carl
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-08-31 12:28:24
* @Entity generator.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}
