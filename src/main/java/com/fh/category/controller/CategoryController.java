package com.fh.category.controller;

import com.fh.category.service.CategoryService;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("queryCategoryList")
    @Ignore
    @ApiOperation(value="查询分类")
    public ServerResponse queryCategoryList(){
        return categoryService.queryCategoryList();
    }

}
