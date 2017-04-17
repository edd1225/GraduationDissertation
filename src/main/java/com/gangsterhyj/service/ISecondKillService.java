package com.gangsterhyj.service;

import com.gangsterhyj.model.UserInfo;

/**
 * Created by gangsterhyj on 17-2-17.
 */
public interface ISecondKillService {
    /**
     * 秒杀操作
     * @param type: 优惠券的类型, 0: 5万; 1: 10万; 2: 15万
     * @param userInfo: 用户信息
     * @return 是否抢成功
     */
    boolean secondKill(Integer type, UserInfo userInfo);

}
