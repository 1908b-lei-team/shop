package com.fh.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.order.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
