package com.fh.order.service.orderInfoService;

import com.fh.order.mapper.OrderInfoMapper;
import com.fh.order.model.OrderInfo;
import com.fh.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Resource
    private OrderInfoMapper orderInfoMapper;


    @Override
    public void insert(OrderInfo orderInfo) {
        orderInfoMapper.insert(orderInfo);
    }
}
