package com.gangsterhyj.domain;

import lombok.*;

/**
 * Created by gangsterhyj on 17-2-23.
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String nickname;
    private String passwordR;
}
