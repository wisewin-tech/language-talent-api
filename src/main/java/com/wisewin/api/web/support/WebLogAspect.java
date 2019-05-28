package com.wisewin.api.web.support;

import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect extends BaseCotroller {
 
    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);


    @Pointcut("execution( * com.wisewin.api.web.controller..*.*(..))")//两个..代表所有子目录，最后括号里的两个..代表所有参数
    public void logPointCut() {
    }
 
 
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        UserBO loginUser = super.getLoginUser(request);
        // 记录下请求内容
        logger.info("user-"+loginUser+"\t"+"url-"+request.getRequestURL()+"\t"+"args-"+Arrays.toString(joinPoint.getArgs()));
    }
 

}
