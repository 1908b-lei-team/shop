package com.fh.member.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.ServerResponse;
import com.fh.member.mapper.MemberMapper;
import com.fh.member.model.Member;
import com.fh.redis.RedisUtil;
import com.fh.util.MD5Util;
import com.fh.util.SystemConstant;
import com.fh.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    @Override
    public ServerResponse checkMemberName(String name) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Member member = memberMapper.selectOne(queryWrapper);
        if(member != null){
            return ServerResponse.error("会员名已存在");
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse checkMemberPhone(String phone) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        Member member = memberMapper.selectOne(queryWrapper);
        if(member != null){
            return ServerResponse.error("手机号已存在");
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse register(Member member) {
        String codeRedis = RedisUtil.get(member.getPhone());
        if(StringUtils.isBlank(codeRedis)){
            return ServerResponse.error("验证码已失效");
        }
        if(! codeRedis.equals(member.getCode())){
            return ServerResponse.error("验证码不正确");
        }
        member.setPassword(MD5Util.generate(member.getPassword()));
        memberMapper.insert(member);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse login(Member member, HttpServletRequest request) {

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",member.getName());
        queryWrapper.or();
        queryWrapper.eq("phone",member.getName());
        Member memberDB = memberMapper.selectOne(queryWrapper);
        if(memberDB == null){
            return ServerResponse.error("会员名或手机号不存在");
        }
        if(!MD5Util.verify(member.getPassword(),memberDB.getPassword())){
            return ServerResponse.error("密码不正确");
        }

        //用户名密码全部正确 token
        String token = null;
        try {
            String jsonString = JSONObject.toJSONString(memberDB);


            //RedisUtil.set("userId",memberDB.getId().toString());
            String encode = URLEncoder.encode(jsonString, "utf-8");
            token = TokenUtil.sign(encode);
            RedisUtil.set(SystemConstant.TOKEN_KEY+token,token,SystemConstant.TOKEN_EXPIRY_TIME);
            return ServerResponse.success(token);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }
}
