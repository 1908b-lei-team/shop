package com.fh.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.Ignore;
import com.fh.common.LoginException;
import com.fh.common.MyException;
import com.fh.member.model.Member;
import com.fh.redis.RedisUtil;
import com.fh.util.SystemConstant;
import com.fh.util.TokenUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLDecoder;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"x-auth,content-type,mtoken");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"PUT,POST,DELETE,GET");
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return false;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

       if(method.isAnnotationPresent(Ignore.class)){
            return true;
        }
        String token = request.getHeader("admin-token");
        //TokenUtil通过可识别的token获得decode 加密后的json字符串
        Member member = null;

        Boolean exists = RedisUtil.exists(SystemConstant.TOKEN_KEY + token);
        if(!exists){
            throw new LoginException();
        }
        if(StringUtils.isNotBlank(token)){
            String encode = TokenUtil.getMember(token);
            //加密后的json字符串解码成json字符串
            String memberJson = URLDecoder.decode(encode,"utf-8");
            //将json字符串转换成对象
            member = JSONObject.parseObject(memberJson, Member.class);
            if(member != null){
                request.getSession().setAttribute(SystemConstant.SESSION_KEY,member);
                //RedisUtil.set(SystemConstant.SESSION_KEY,memberJson);
                request.getSession().setAttribute(SystemConstant.TOKEN_KEY,token);
                return true;
            }else{
                throw new LoginException();
            }
        }
        return false;

    }





}
