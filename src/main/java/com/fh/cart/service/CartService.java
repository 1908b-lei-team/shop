package com.fh.cart.service;


import com.fh.cart.model.Cart;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    ServerResponse buy(Cart cart, HttpServletRequest request);


    ServerResponse queryCartListCount(Member member);

    ServerResponse queryCartList(Member member);

    ServerResponse buildOrderList(Member member, String listJson);
}
