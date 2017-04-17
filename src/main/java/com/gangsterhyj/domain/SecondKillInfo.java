package com.gangsterhyj.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * Created by gangsterhyj on 17-2-17.
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class SecondKillInfo {
    /**
     * 优惠券类型
     */
    private int type;
    /**
     * 用户的用户名
     */
    private String username;
    /**
     * 抢杀时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date time;
}
