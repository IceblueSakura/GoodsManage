package com.example.goodsmanage;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.goodsmanage.entity.TokenInfo;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Calendar;


/**
 * 杂七杂八工具类
 * 这个类全是static静态方法，代表着不用new Utils()建立实例就能直接使用这些方法
 */

public class Utils {

    public static String md5HashCode(InputStream file) {  // 从文件流计算HASH
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = file.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            file.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes); //1代表绝对值
            return bigInt.toString(16);//转换为16进制
        } catch (Exception e) {
            e.printStackTrace();
            return "";  // 出错了，返回空白的
        }
    }

    private static final String jwtToken = "GoodsManage";  // 用于生成和验证登陆后token的密钥，不能泄露

    public static String generateToken(Integer sid, String name) {  // 生成token
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        return JWT.create()
                .withClaim("sid", sid)//payload  //自定义用户名
                .withClaim("name", name)
                .withExpiresAt(instance.getTime()) //指定令牌过期时间
                .sign(Algorithm.HMAC256(jwtToken));
    }

    public static TokenInfo getInfoByToken(String token) {  //从token内获取用户信息
        TokenInfo tokenInfo = new TokenInfo();
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtToken)).build().verify(token);
            tokenInfo.setSid(decodedJWT.getClaim("sid").asInt());
            tokenInfo.setUsername(decodedJWT.getClaim("name").asString());
        } catch (Exception e) {
            return null;
        }
        return tokenInfo;
    }

}
