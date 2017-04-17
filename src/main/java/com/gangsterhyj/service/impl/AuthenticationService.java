package com.gangsterhyj.service.impl;

import com.gangsterhyj.model.UserInfo;
import com.gangsterhyj.service.IAuthenticationService;
import com.gangsterhyj.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * Created by gangsterhyj on 17-2-17.
 */
@Service
public class AuthenticationService implements IAuthenticationService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;
    public String getToken(UserInfo userInfo) {
        String token = "";
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(secret, expiration);
        try {
            token = jwtTokenUtil.generateToken(userInfo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }
}
