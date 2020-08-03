package com.fh.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.order.model.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    void batchAdd(List<OrderInfo> orderInfoList);
}
