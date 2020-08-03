package com.fh.member.controller;

import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.member.service.MemberService;
import com.fh.redis.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("member")
public class MemberController {
    @Resource
    private MemberService memberService;
    @RequestMapping("checkMemberName")
    @Ignore
    public ServerResponse checkMemberName(String name){
        return memberService.checkMemberName(name);
    }
    @RequestMapping("checkMemberPhone")
    @Ignore
    public ServerResponse checkMemberPhone(String phone){
        return memberService.checkMemberPhone(phone);
    }
    @RequestMapping("register")
    @Ignore
    public ServerResponse register(Member member){
        return memberService.register(member);
    }
    @RequestMapping("login")
    @Ignore
    public ServerResponse login(Member member, HttpServletRequest request){
        return memberService.login(member,request);
    }
    @RequestMapping("checkLogin")
    @Ignore
    public ServerResponse checkLogin(HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute(SystemConstant.SESSION_KEY);
        if(member == null){
            return ServerResponse.error();
        }
        return ServerResponse.success();
    }
    @RequestMapping("logout")
    @Ignore
    public ServerResponse logout(HttpServletRequest request){
        String token = (String) request.getSession().getAttribute(SystemConstant.TOKEN_KEY);
        RedisUtil.del(SystemConstant.TOKEN_KEY+token);
        request.getSession().removeAttribute(SystemConstant.TOKEN_KEY);
        request.getSession().removeAttribute(SystemConstant.SESSION_KEY);
        return ServerResponse.success();
    }

}
