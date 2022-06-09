package com.example.goodsmanage.services;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.goodsmanage.entity.UserVO;
import com.example.goodsmanage.model.User;

/**
* 针对表【USERS】的数据库操作Service接口
*/
public interface UserService extends IService<User> {
    /**
     * 根据sid或username查询用户的全部信息，并转换为UserDTO类型
     * @param sid 用户sid
     * @param username 用户名
     * @return UserDTO
     */
    UserVO selectOneUser(Integer sid, String username);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 查询到的用户实体
     */
    User login(String username,String password);

    /**
     * 更新用户信息(不包括密码)
     * @param user 用户信息实体
     */
    void modifiedUser(User user);

    /**
     * 注册用户
     * @param user 用户信息实体
     */
    void register(User user);
}
