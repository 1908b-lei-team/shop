package com.fh.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.product.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ProductMapper extends BaseMapper<Product> {


    Long updateStockById(@Param("count") int count, @Param("productId")Integer productId);
}
