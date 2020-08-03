package com.fh.address.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.address.model.Address;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface AddressMapper extends BaseMapper<Address> {


    void updStatus(Integer status);
}
