package com.gangsterhyj.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by gangsterhyj on 17-2-18.
 */
@Aspect
@Component
@Slf4j
public class WebRequestLogAspect {

    private ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    @Pointcut("execution(public * com.gangsterhyj.api..*.*(..))")
    public void webLog() {}

    @Before("webLog()")
    public void doBefore(JoinPoint point) {
        // 接收到请求，记录请求内容
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("URL: " + request.getRequestURL().toString());
        log.info("HTTP_METHOD: " + request.getMethod());
        log.info("IP: " + request.getRemoteAddr());
        log.info("CLASS_METHOD: " + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        log.info("ARGUMENTS: " + Arrays.toString(point.getArgs()));
    }

    @AfterReturning(returning = "response", pointcut = "webLog()")
    public void doAfterReturning(Object response) throws Throwable {
        log.info("RESPONSE: " + response);
        log.info("SPEND TIME: " + (System.currentTimeMillis() - startTime.get()) + "ms");
    }

}
