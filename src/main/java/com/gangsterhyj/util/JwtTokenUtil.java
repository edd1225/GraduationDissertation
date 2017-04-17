package com.gangsterhyj.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gangsterhyj.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by gangsterhyj on 17-2-21.
 */
@Slf4j
public class JwtTokenUtil {

    private String secret;
    private long expiration;

    public JwtTokenUtil(String secret, long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public  Date getExpirationDateFromToken(String authToken) {
        return JWT.decode(authToken).getExpiresAt();
    }

    public  String getUsernameFromToken(String authToken) {
        return JWT.decode(authToken).getSubject();
    }

    /**
     * 判断JWT是否过期
     * @param authToken
     */
    private  boolean isTokenExpired(String authToken) {
        return getExpirationDateFromToken(authToken).before(new Date());
    }


    public  String getAudienceFromToken(String authToken) {
        return JWT.decode(authToken).getAudience().get(0);
    }

    public  boolean validateToken(String authToken) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            try {
                verifier.verify(authToken);
            } catch (JWTVerificationException e) {
                throw new RuntimeException("token无效，请重新登录");
            }
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        return true;
    }

    /**
     * 根据用户信息生成jwt
     * @param userInfo
     */
    public  String generateToken(UserInfo userInfo) throws UnsupportedEncodingException {
        return JWT.create()
                .withSubject(userInfo.getUsername())
                .withAudience(userInfo.getId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration * 1000))
                .sign(Algorithm.HMAC256(secret));
    }

}
