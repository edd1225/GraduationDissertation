package com.gangsterhyj.api;

import com.gangsterhyj.annotation.CurrentUser;
import com.gangsterhyj.annotation.LoginRequired;
import com.gangsterhyj.domain.LoginRequest;
import com.gangsterhyj.model.UserInfo;
import com.gangsterhyj.response.FormatResponse;
import com.gangsterhyj.response.ResultCode;
import com.gangsterhyj.service.IUserInfoService;
import com.gangsterhyj.service.impl.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gangsterhyj on 17-2-17.
 */
@Slf4j
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private IUserInfoService userInfoService;
    @PostMapping("/test")
    @LoginRequired
    FormatResponse test(@CurrentUser UserInfo userInfo) {
        return new FormatResponse(userInfo);
    }
    @PostMapping("/login")
    public FormatResponse login(@RequestBody LoginRequest loginRequest) {
        UserInfo userInfo = userInfoService.selectByUsername(loginRequest.getUsername());
        Map<String, Object> result = new HashMap<String, Object>();
        UserInfo userInput = new UserInfo();
        userInput.setUsername(loginRequest.getUsername());
        userInput.setPassword(loginRequest.getPassword());
        if (userInfo == null) {
            return new FormatResponse(ResultCode.USER_NOT_EXIST);
        } else if (!userInfoService.comparePassword(userInput, userInfo)) {
            return new FormatResponse(ResultCode.PASSWORD_ERROR);
        } else {
            String token = authenticationService.getToken(userInfo);
            result.put("token", token);
            result.put("user", userInfo);
        }
        return new FormatResponse(result);
    }


}
