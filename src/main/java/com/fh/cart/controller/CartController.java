package com.fh.cart.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.cart.model.Cart;
import com.fh.cart.service.CartService;
import com.fh.common.MemberAnnotation;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.redis.RedisUtil;
import com.fh.util.SystemConstant;
import net.sf.json.JSONArray;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {
    @Resource
    private CartService cartService;

    @RequestMapping("buy")
    public ServerResponse buy(Cart cart, HttpServletRequest request){
        return cartService.buy(cart,request);
    }

    @RequestMapping("queryCartListCount")
    public ServerResponse queryCartListCount(@MemberAnnotation Member member){
        return cartService.queryCartListCount(member);
    }

    @RequestMapping("queryCartList")
    public ServerResponse queryCartList(@MemberAnnotation Member member){
        return cartService.queryCartList(member);
    }
    @RequestMapping("del/{productId}")
    public ServerResponse del(@MemberAnnotation Member member, @PathVariable("productId") Integer productId){
        RedisUtil.hdel(SystemConstant.REDIS_CART_KEY +member.getId(),productId.toString());
        return cartService.queryCartList(member);
    }

    @RequestMapping("batchDel")
    public ServerResponse batchDel(@MemberAnnotation Member member, @RequestParam("idList") List<Integer> idList){
        for (Integer productId : idList) {
            RedisUtil.hdel(SystemConstant.REDIS_CART_KEY +member.getId(),productId.toString());
        }
        return cartService.queryCartList(member);
    }

    @RequestMapping("buildOrderList")
    public ServerResponse buildOrderList(@MemberAnnotation Member member, String listJson){



        return cartService.buildOrderList(member,listJson);
    }

    public static void main(String[] args) {
        String list = "[{\"count\":4,\"productId\":13,\"filePath\":\"http://1908b.oss-cn-beijing.aliyuncs.com/ac577213-3dfd-4430-aa78-000f4867179a.png\",\"price\":1200,\"name\":\"2020年新款温柔风雪纺连衣裙女夏轻熟风气质裙子法式显瘦显高长裙\"},{\"count\":3,\"productId\":1,\"filePath\":\"http://1908b.oss-cn-beijing.aliyuncs.com/186e31d0-559e-44d1-8e11-8d7af666b9fd.png\",\"price\":234,\"name\":\"ins小众设计可调节一字母手链首饰女时尚百搭显白网红气质手饰品\"}]";
        //Object parse = JSONObject.parse(list);
        List<Cart> cartList = JSONObject.parseArray(list, Cart.class);
        for (Cart cart : cartList) {
            System.out.println(cart.getName());
        }

    }
}
