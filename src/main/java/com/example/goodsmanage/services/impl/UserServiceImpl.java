package com.example.goodsmanage.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.goodsmanage.entity.UserVO;

import com.example.goodsmanage.mapper.UserMapper;
import com.example.goodsmanage.model.User;
import com.example.goodsmanage.mapper.FileMapper;
import com.example.goodsmanage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 针对表【USERS】的数据库操作Service接口的实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    final private FileMapper fileMapper;
    final private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, FileMapper fileMapper) {
        this.userMapper = userMapper;
        this.fileMapper = fileMapper;
    }

    /**
     * 根据sid或username查询用户的全部信息，并转换为UserDTO类型
     *
     * @param sid      用户sid
     * @param username 用户名
     * @return UserDTO
     */
    @Override
    public UserVO selectOneUser(Integer sid, String username) {
        User user = null;
        if (sid != null && username != null) {
            user = userMapper.findOneBySidAndUsername(sid, username);
        } else if (sid != null) {
            user = userMapper.findBySid(sid);
        } else if (username != null) {
            user = userMapper.findByUsername(username);
        }
        if (user == null) {
            return null;
        }

        return new UserVO(user, fileMapper.findOneByFid(user.getAvatarFid()));
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 查询到的用户实体
     */
    @Override
    public User login(String username, String password) {

        User user = userMapper.findOnePasswordByUsername(username);  //只查询密码
        if (user != null && user.getPassword().equals(password)) {         //目前待拓展：使用password+salt后哈希再存入数据库密码
            User userInfo = userMapper.findByUsername(username);
            userInfo.setLastLogin(new Date());  // 更新最后登录时间为当前服务器时间
            userMapper.updateById(userInfo);
            return userInfo;
        }
        return null;  // 登录失败
    }

    /**
     * 更新用户信息(不包括密码)
     * @param user 用户信息实体
     */
    @Override
    public void modifiedUser(User user) {
        userMapper.updateInfo(user);
    }

    @Override
    public void register(User user) {
        user.setLastLogin(new Date());  // 设置默认最后登录时间为现在
        userMapper.insert(user);
    }
}




