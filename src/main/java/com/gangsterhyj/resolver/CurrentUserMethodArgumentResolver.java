package com.gangsterhyj.resolver;

import com.gangsterhyj.annotation.CurrentUser;
import com.gangsterhyj.model.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * Created by gangsterhyj on 17-2-17.
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(UserInfo.class)
                && methodParameter.hasParameterAnnotation(CurrentUser.class);
    }


    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        UserInfo user = (UserInfo) nativeWebRequest.getAttribute("currentUser", RequestAttributes.SCOPE_REQUEST);
        if (user != null) {
            return user;
        }
        throw new MissingServletRequestPartException("currentUser");
    }
}
