package com.example.goodsmanage.mapper;
import org.apache.ibatis.annotations.Param;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.goodsmanage.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
* 针对表【USERS】的数据库操作Mapper
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findBySid(@Param("sid") Integer sid);

    User findByUsername(@Param("username") String username);

    User findOneBySidAndUsername(@Param("sid") Integer sid, @Param("username") String username);  // 用户名和id都符合的用户

    User findOnePasswordByUsername(@Param("username") String username);  // 根据用户名查找密码，用于登陆验证

    int updateInfo(User user);


}




