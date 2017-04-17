package com.gangsterhyj.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.gangsterhyj.annotation.LoginRequired;
import com.gangsterhyj.model.UserInfo;
import com.gangsterhyj.service.IUserInfoService;
import com.gangsterhyj.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by gangsterhyj on 17-2-17.
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserInfoService userInfoService;
    @Value("${jwt.secret}")
    private  String secret;

    @Value("${jwt.expiration}")
    private  Long expiration;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        // 如果不是映射到方法，那么直接跳过
        if (!(handler instanceof HandlerMethod))
            return true;
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(secret, expiration);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断方法是否需要登录验证
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        if (methodAnnotation != null) {
            // 执行认证
            String authHeader= httpServletRequest.getHeader("Authorization");
            log.info(authHeader);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("无token，请重新登录");
            }
            String token = authHeader.substring(7);
            int userId;
            try {
                userId = Integer.parseInt(jwtTokenUtil.getAudienceFromToken(token)/*JWT.decode(token).getAudience().get(0)*/);
            } catch (JWTDecodeException e) {
                throw new RuntimeException("token无  效，请重新登录");
            }
            log.info("用户id是: " + userId);
            UserInfo userInfo = userInfoService.selectById(userId);
            if (userInfo == null)
                throw new RuntimeException("用户不存在，请重新注册");
            // 验证token
            log.info(token);
            if (jwtTokenUtil.validateToken(token))
            // try {
            //     JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            //     try {
            //         verifier.verify(token);
            //     } catch (JWTVerificationException e) {
            //         throw new RuntimeException("token无效，请重新登录");
            //     }
            // } catch (UnsupportedEncodingException e) {
            //     throw new RuntimeException("token无效，请重新登录");
            // }
                httpServletRequest.setAttribute("currentUser", userInfo);
            else
                return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
