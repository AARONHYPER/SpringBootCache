package com.aaron.hyper.mapper;

import com.aaron.hyper.po.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by AARON on 2017/7/15.
 */
@Mapper
public interface ProductMapper {

    Product select(long productId);

    int update(Product product);
}
