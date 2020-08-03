package com.fh.product.controller;

import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.product.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Resource
    private ProductService productService;
    @RequestMapping("queryProductList")
    @Ignore
    public ServerResponse queryProductList(){
        return productService.queryProductList();
    }
    @RequestMapping("queryHotProductList")
    @Ignore
    public ServerResponse queryHotProductList(){
        return productService.queryHotProductList();
    }
    @RequestMapping("queryProductListPage")
    @Ignore
    public ServerResponse queryProductListPage(Long current,Long pageSize){
        return productService.queryProductListPage(current,pageSize);
    }
    @RequestMapping("queryProductById")
    @Ignore
    public ServerResponse queryProductById(Integer id){
        return productService.queryProductById(id);
    }



}
