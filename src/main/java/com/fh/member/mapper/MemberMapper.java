package com.fh.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.member.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
