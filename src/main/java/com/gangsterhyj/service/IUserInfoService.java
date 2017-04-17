package com.gangsterhyj.service;

import com.gangsterhyj.model.UserInfo;

import java.util.Date;

/**
 * Created by gangsterhyj on 17-1-17.
 */
public interface IUserInfoService {
    UserInfo selectById(int id);

    UserInfo selectByUsername(String username);

    Integer insert(String username, String password, String nickname, Date registered);

    boolean comparePassword(UserInfo userInput, UserInfo userInfo);

    String passwordToHash(String password);
}
