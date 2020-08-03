package com.fh.order.service.orderService;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.address.model.Address;
import com.fh.address.service.AddressService;
import com.fh.cart.model.Cart;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.order.mapper.OrderInfoMapper;
import com.fh.order.mapper.OrderMapper;
import com.fh.order.model.Order;
import com.fh.order.model.OrderInfo;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import com.fh.redis.RedisUtil;
import com.fh.util.IdUtil;
import com.fh.util.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private AddressService addressService;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private OrderMapper orderMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public ServerResponse buildOrderList(Member member, String listJson, Integer addressId) {
        if(StringUtils.isBlank(listJson)){
            return ServerResponse.error("结算购物车为空");
        }

        String orderNo = IdUtil.createId();
        int totalCount = 0;
        BigDecimal totalPrice = new BigDecimal(0);
        List<OrderInfo> orderInfoList = new ArrayList<>();
        List<Product> stockNotFullList = new ArrayList<>();
        List<Cart> cartList = JSONObject.parseArray(listJson, Cart.class);
        for (Cart cart : cartList) {
            Product product =productService.isExist(cart.getProductId());
            if(cart.getCount() > product.getStock()){
                //库存不足
                stockNotFullList.add(product);
                continue;
            }
            //
            //减库存操作
            Long res = productService.updateStockById(cart.getCount(),cart.getProductId());
            if(res == 1){
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setOrderId(orderNo);

                orderInfo.setCount(cart.getCount());
                totalCount+=cart.getCount();

                orderInfo.setFilePath(cart.getFilePath());
                orderInfo.setName(cart.getName());

                orderInfo.setPrice(cart.getPrice());

                orderInfo.setProductId(cart.getProductId());
                BigDecimal bigDecimal = new BigDecimal(cart.getCount());
                orderInfo.setSubTotalPrice(bigDecimal.multiply(cart.getPrice()));
                totalPrice = totalPrice.add(orderInfo.getSubTotalPrice());

                orderInfoList.add(orderInfo);


            }else{
                stockNotFullList.add(product);
            }

        }
        if(orderInfoList != null && orderInfoList.size() == cartList.size()){
            //批量增加、
            orderInfoMapper.batchAdd(orderInfoList);
            //修改radis 购物车
            for (OrderInfo orderInfo : orderInfoList) {
                changeRedisCart(member,orderInfo);
            }
            Order order = getOrder(member, addressId, orderNo, totalCount, totalPrice);
            orderMapper.insert(order);
            order.setList(orderInfoList);
            String orderToString = JSONObject.toJSONString(order);
            //放入普通转换，但不提供custom 使其失效
            rabbitTemplate.convertAndSend("ex", "queue", orderToString);
            return ServerResponse.success(orderNo);
        }else{
            return ServerResponse.error(stockNotFullList);
        }



    }

    private Order getOrder(Member member, Integer addressId, String orderNo, int totalCount, BigDecimal totalPrice) {
        Order order = new Order();
        order.setAddressId(addressId);
        order.setCreateDate(new Date());
        order.setId(orderNo);
        order.setMemberId(member.getId());
        order.setPayType(1);
        order.setStatus(0);
        order.setTotalCount(totalCount);
        order.setTotalPrice(totalPrice);
        return order;
    }

    private void changeRedisCart(Member member, OrderInfo orderInfo) {
        String cartJson = RedisUtil.hGet(SystemConstant.REDIS_CART_KEY + member.getId(), orderInfo.getProductId().toString());
        Cart cartRedis = JSONObject.parseObject(cartJson, Cart.class);
        if(orderInfo.getCount() >= cartRedis.getCount()){
            RedisUtil.hdel(SystemConstant.REDIS_CART_KEY + member.getId(), orderInfo.getProductId().toString());
        }else{
            cartRedis.setCount(cartRedis.getCount()-orderInfo.getCount());
            String jsonString = JSONObject.toJSONString(cartRedis);
            RedisUtil.hSet(SystemConstant.REDIS_CART_KEY + member.getId(), orderInfo.getProductId().toString(),jsonString);
        }
    }

    @Override
    public ServerResponse queryOrderList() {
        QueryWrapper<Order> queryWrappe = new QueryWrapper<>();
        queryWrappe.orderByAsc("status");
        List<Order> orderList = orderMapper.selectList(queryWrappe);
        for (Order order : orderList) {
            Address address= addressService.selectOne(order.getAddressId());
            order.setAddress(address);
            QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("orderId",order.getId());
            List<OrderInfo> infoList = orderInfoMapper.selectList(queryWrapper);
            order.setList(infoList);
        }
        return ServerResponse.success(orderList);
    }

    @Override
    public ServerResponse changeOrderStatus(String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",orderNo);
        Order order = orderMapper.selectOne(queryWrapper);
        order.setStatus(1);
        orderMapper.updateById(order);
        return ServerResponse.success();
    }

    @Override
    public void update(Order order) {
        orderMapper.updateById(order);
    }

    @Override
    public Order queryOrderById(String id) {
        return orderMapper.selectById(id);
    }
}
