package com.fh.ttl;

import com.alibaba.fastjson.JSONObject;
import com.fh.order.model.Order;
import com.fh.order.model.OrderInfo;
import com.fh.order.service.orderService.OrderService;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TTL-消费者
 */
@Component
public class TTLConsumer {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    /**
     * 消费者
     *
     * @param message 消息体
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(queues = "ttl-queue")
    public void process(Message message) throws Exception {
        String orderToString = new String(message.getBody(), "UTF-8");
        Order order = JSONObject.parseObject(orderToString, Order.class);
        //查询数据库订单状态
        order =  orderService.queryOrderById(order.getId());
        if(order.getStatus() == 0){
            //将order的状态设为3 商品订单超时状态
            order.setStatus(3);
            orderService.update(order);
            //未支付状态 取出orderInfoList的库存还给product
            List<OrderInfo> list = order.getList();
            for (OrderInfo orderInfo : list) {
                int count = orderInfo.getCount();
                Product product = productService.isExist(orderInfo.getProductId());
                product.setStock(product.getStock()+count);
                productService.update(product);
            }

        }
    }

}
