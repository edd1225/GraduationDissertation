package com.gangsterhyj.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Created by gangsterhyj on 17-1-17.
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class UserInfo {
    private Integer id;
    private String username;//用户名,也就是手机号
    private String password;//md5加密后的密码
    private String nickname;
    @JsonFormat(pattern="yyyy年MM月dd日 HH:mm")
    private Date registered;
}
