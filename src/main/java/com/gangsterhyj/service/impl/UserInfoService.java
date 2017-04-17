package com.gangsterhyj.service.impl;

import com.gangsterhyj.dao.UserInfoMapper;
import com.gangsterhyj.model.UserInfo;
import com.gangsterhyj.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by gangsterhyj on 17-1-17.
 */
@Service
@Slf4j
public class UserInfoService implements IUserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public UserInfo selectById(int id) {
        return userInfoMapper.selectById(id);
    }

    @Override
    public UserInfo selectByUsername(String username) {
        return userInfoMapper.selectByUsername(username);
    }

    @Override
    public Integer insert(String username, String password, String nickname, Date registered) {
        return userInfoMapper.insert(username, password, nickname, registered);
    }

    @Override
    public boolean comparePassword(UserInfo userInput, UserInfo userInfo) {
        return passwordToHash(userInput.getPassword()).equals(userInfo.getPassword());
    }

    public String passwordToHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            byte[] src = digest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            // 字节数组转16进制字符串
            // https://my.oschina.net/u/347386/blog/182717
            for (byte aSrc : src) {
                String s = Integer.toHexString(aSrc & 0xFF);
                if (s.length() < 2) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException ignore) {
        }
        return null;
    }
}
