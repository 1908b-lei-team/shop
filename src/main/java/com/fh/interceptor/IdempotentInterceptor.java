package com.fh.interceptor;

import com.fh.common.Idempotent;
import com.fh.common.MyException;
import com.fh.redis.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class IdempotentInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        if(! method.getMethod().isAnnotationPresent(Idempotent.class)){
            return true;
        }
        String mtoken = request.getHeader("mtoken");
        if(StringUtils.isBlank(mtoken)){
            throw new MyException("mtoken为空");
        }
        Boolean exists = RedisUtil.exists(mtoken);
        if(! exists){
            throw new MyException("mtoken失效");
        }
        Long del = RedisUtil.del(mtoken);
        if(del==0){
            throw new MyException("商品重复下单");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
