package com.example.goodsmanage.entity;

import com.example.goodsmanage.model.FileModel;
import com.example.goodsmanage.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

/**
 * 用来传递给前端页面显示用户信息的ViewObject视图对象
 */

@Data
@NoArgsConstructor
public class UserVO {  // 用来向页面传递User信息的视图对象(View Object,VO)

    public UserVO(User user, FileModel fileModel) {  // UserVO既要显示商品信息，也要传递商品图片，所以同时接收User和FileModel对象并且取出需要的数据
        this.sid = user.getSid();
        this.username = user.getUsername();
        this.avatarUrl = "/file/fid/" + fileModel.getFid();  // 拼接头像图片地址
        this.avatarFid = fileModel.getFid();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.lastLogin = simpleDateFormat.format(user.getLastLogin());
        this.detailsText = user.getDetailsText();
        this.password = user.getPassword();
    }

    private Integer sid;

    private String username;

    private String avatarUrl;

    private Integer avatarFid;

    private String lastLogin;

    private String detailsText;

    private String password;

}