package com.example.goodsmanage.entity;

import lombok.Data;

/**
 * 用来从Token里获取信息的实体类
 */
@Data
public class TokenInfo {
    private Integer sid; // 用户sid
    private String username;  // 用户名
}
