package com.moxi.common.config;

import com.moxi.domain.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 接口签名拦截器
 * @author hgl
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    private static final List<String> ALLOWED_PATHS = Collections.unmodifiableList(new ArrayList<>(
            Arrays.asList("/swagger*","/webjars/**","/v2/**","/actuator/**")));



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String uri = request.getRequestURI();
        logger.info(">>>preHandle>>>>>>>"+uri);
        if(uri.indexOf("admin/login")>0){
            return true;
        }
        Admin user =  (Admin)request.getSession().getAttribute("admin");
        if(user == null){
            /**
             * 拦截目录下请求，是否为ajax请求
             *   是：无需登录，直接访问（因为我是用于首页的ajax登录请求）
             *   否：跳转至登录界面
             */
            if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
                //如果是ajax请求响应头会有，x-requested-with
                logger.info("发生ajax请求...");
                return true;
            }else{
                logger.info("用户未登录跳转至登录页面");
                //转发到登录界面
                response.sendRedirect("/admin/login");
            }
            return false;
        }else{
            return true;
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

}
