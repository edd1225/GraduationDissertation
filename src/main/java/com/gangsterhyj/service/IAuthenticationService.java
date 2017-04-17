package com.gangsterhyj.service;

import com.gangsterhyj.model.UserInfo;

/**
 * Created by gangsterhyj on 17-2-17.
 */
public interface IAuthenticationService {
    String getToken(UserInfo userInfo);
}
