package com.gangsterhyj.api;

import com.gangsterhyj.annotation.CurrentUser;
import com.gangsterhyj.annotation.LoginRequired;
import com.gangsterhyj.model.UserInfo;
import com.gangsterhyj.response.FormatResponse;
import com.gangsterhyj.response.ResultCode;
import com.gangsterhyj.service.ISecondKillService;
import com.gangsterhyj.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gangsterhyj on 17-2-17.
 */
@RestController
@RequestMapping("/api/secondkill")
@Slf4j
public class SecondKillController {
    @Autowired
    ISecondKillService secondKillService;
    @Autowired
    IUserInfoService userInfoService;

    @GetMapping("")
    @LoginRequired
    public FormatResponse seccondkill(@RequestParam(value="type") Integer type, @CurrentUser UserInfo userInfo)  {
        log.info("type " + type);
        log.info("userinfo " + userInfo);
        boolean result;
        try {
            result = secondKillService.secondKill(type, userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new FormatResponse(ResultCode.EXCEPTION, e.getMessage());
        }
        return new FormatResponse(result);
    }
}
