package com.fh.order.service.orderService;


import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.order.model.Order;

public interface OrderService {


    ServerResponse buildOrderList(Member member, String listJson, Integer addressId);

    ServerResponse queryOrderList();

    ServerResponse changeOrderStatus(String orderNo);

    void update(Order order);

    Order queryOrderById(String id);
}
