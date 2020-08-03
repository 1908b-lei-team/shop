package com.fh.product.service;

import com.fh.common.ServerResponse;
import com.fh.product.model.Product;

import java.util.List;

public interface ProductService {

    ServerResponse queryProductList();

    ServerResponse queryHotProductList();

    ServerResponse queryProductListPage(Long current, Long pageSize);

    ServerResponse queryProductById(Integer id);

    Product isExist(Integer productId);

    Long updateStockById(int count, Integer productId);

    void update(Product product);
}
