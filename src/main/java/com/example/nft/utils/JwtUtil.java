package com.example.nft.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.nft.service.ex.LoginException;

import java.util.Date;

public class JwtUtil {
    /**
     * 过期12小时
     * */
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    /**
     * jwt密钥
     * */
    private static final String SECRET = "jwt_secret";

    /**
     * @param
     * @return
     * */
    public static String genToken(String user) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);

            return JWT.create()
                    //将userId保存到token里面
                    .withAudience(user)
                    //存放自定义数据
//                    .withClaim("store", store)
                    //五分钟后token过期
                    .withExpiresAt(date)
                    //token的密钥
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据token获取userId
     * @return
     * */
    public static String getUser(String token) {
        try {
            String user = JWT.decode(token).getAudience().get(0);
            return user;
        }catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 根据token获取自定义数据info
     * @param token
     * @return
     * */
    public static String getStore(String token) {
        try {

            return JWT.decode(token).getClaim("store").asString();
        }catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 校验token
     * @param token
     * @return
     * */
    public static boolean checkSign(String token) {
        try {
            Algorithm algorithm  = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    //.withClaim("username, username)
                    .build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new LoginException("token 无效，请重新获取");
        }
    }
}
