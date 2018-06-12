package com.ningpai.common.aspect;

import com.ningpai.site.customer.vo.CustomerConstantStr;
import org.aspectj.lang.annotation.Aspect;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;


/**
 * Created by youzipi on 16/10/12.
 */
@Component
@Aspect
public class LoginAspect {
    private static final Log log = LogFactory.getLog(LoginAspect.class);


    @Pointcut(value = "@annotation( com.ningpai.common.annotation.LoginRequired)")
    private void loginRequired() {
    }



    @Around(value = "loginRequired()")
    public ModelAndView checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

//        Annotation[] annotations = joinPoint.getClass().getAnnotations();
//        for (Annotation annotation : annotations) {
//            annotation.
//        }

        if (request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID) != null) {
            return (ModelAndView) joinPoint.proceed();
        } else {
            return new ModelAndView("redirect:/login.html");
        }


    }

}