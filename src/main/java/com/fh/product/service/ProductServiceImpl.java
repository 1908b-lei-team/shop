package com.fh.product.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.common.ServerResponse;
import com.fh.product.mapper.ProductMapper;
import com.fh.product.model.Product;
import com.fh.redis.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public ServerResponse queryProductList() {
        List<Product> list = productMapper.selectList(null);
        return ServerResponse.success(list);
    }

    @Override
    public ServerResponse queryHotProductList() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("iSHot",1);
        List<Product> hotList = productMapper.selectList(queryWrapper);
        return ServerResponse.success(hotList);
    }

    @Override
    public ServerResponse queryProductListPage(Long current, Long pageSize) {
        IPage<Product> page = new Page<>(current,pageSize);
        IPage<Product> productIPage = productMapper.selectPage(page, null);
        //一共几页 long pages = productIPage.getPages();
        return ServerResponse.success(productIPage);
    }
    @Override
    public ServerResponse queryProductById(Integer id) {
        return ServerResponse.success(productMapper.selectById(id));
    }

    @Override
    public Product isExist(Integer productId) {
        return productMapper.selectById(productId);
    }

    @Override
    public Long updateStockById(int count, Integer productId) {
        return productMapper.updateStockById( count,  productId);
    }

    @Override
    public void update(Product product) {
        productMapper.updateById(product);
    }


}
