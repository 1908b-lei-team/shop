package com.fh.order.controller;

import com.fh.common.Idempotent;
import com.fh.common.MemberAnnotation;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.order.service.orderService.OrderService;
import com.fh.redis.RedisUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @RequestMapping("buildOrderList")
    @Idempotent
    public ServerResponse buildOrderList(@MemberAnnotation Member member, String listJson,Integer addressId){
        return orderService.buildOrderList(member,listJson,addressId);
    }
    @RequestMapping("queryOrderList")
    public ServerResponse queryOrderList(){
        return orderService.queryOrderList();
    }

    @RequestMapping("changeOrderStatus")
    public ServerResponse changeOrderStatus(String orderNo){
        return orderService.changeOrderStatus(orderNo);
    }

    @RequestMapping("getToken")
    public ServerResponse getToken(){
        String mtoken = UUID.randomUUID().toString();
        RedisUtil.set(mtoken,mtoken,600);
        return ServerResponse.success(mtoken);
    }
}
