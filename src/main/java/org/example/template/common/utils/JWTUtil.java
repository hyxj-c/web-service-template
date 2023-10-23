package org.example.template.common.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * JWT生成token工具类
 */
public class JWTUtil {
    public static final long EXPIRE = 1000 * 60 * 60 * 24; // token过期时间
    public static final String APP_SECRET = "xiaoqi"; // 秘钥（密钥过短或中文都会生成token失败）

    /**
     * 生成token字符串
     * @param id 用户id
     * @param name 用户名
     * @return 生成的token字符串
     */
    public static String generateToken(String id, String name){
        String token = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setHeaderParam("algorithm", "HS256")

                .setSubject("web-template")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                .claim("id", id)  //设置token主体部分 ，存储用户信息
                .claim("name", name)

                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return token;
    }

    /**
     * 判断token是否存在与有效
     * @param token token字符串
     * @return 有效返回true，无效返回false
     */
    public static boolean checkToken(String token) {
        if(StringUtils.hasLength(token)) {
            try {
                Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
                Claims claims = claimsJws.getBody();
                // 判断距离token过期时间是否小于半小时，若小于半小时则重置过期时间
                long expirationTime = claims.getExpiration().getTime();
                if (expirationTime - (System.currentTimeMillis() + 1000 * 60 * 30) < 0) {
                    claims.setExpiration(new Date(expirationTime + EXPIRE));
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 根据token字符串获取用户id
     * @param token token字符串
     * @return 用户id
     */
    public static String getUserIdByJwtToken(String token) {
        if (StringUtils.hasLength(token)) {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String)claims.get("id");
        }
        return null;
    }

    /**
     * 根据token字符串获取用户名
     * @param token token字符串
     * @return 用户名
     */
    public static String getUserNameByJwtToken(String token) {
        if (StringUtils.hasLength(token)) {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String)claims.get("name");
        }
        return null;
    }
}
