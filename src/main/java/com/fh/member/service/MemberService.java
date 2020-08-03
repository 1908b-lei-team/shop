package com.fh.member.service;

import com.fh.common.ServerResponse;
import com.fh.member.model.Member;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {
    ServerResponse checkMemberName(String name);

    ServerResponse checkMemberPhone(String phone);

    ServerResponse register(Member member);

    ServerResponse login(Member member, HttpServletRequest request);
}
