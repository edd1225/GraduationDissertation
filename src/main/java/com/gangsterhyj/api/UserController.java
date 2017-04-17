package com.gangsterhyj.api;

import com.gangsterhyj.domain.RegisterRequest;
import com.gangsterhyj.response.FormatResponse;
import com.gangsterhyj.response.ResultCode;
import com.gangsterhyj.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by gangsterhyj on 17-2-17.
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserInfoService userInfoService;
    @PostMapping("/register")
    public FormatResponse register(@RequestBody RegisterRequest registerRequest) {
        if (userInfoService.selectByUsername(registerRequest.getUsername()) != null) {
            return new FormatResponse(ResultCode.REGISTERED);
        }
        return  new FormatResponse(userInfoService.insert(registerRequest.getUsername(), userInfoService.passwordToHash(registerRequest.getPassword()), registerRequest.getNickname(), new Date()));
    }


}
