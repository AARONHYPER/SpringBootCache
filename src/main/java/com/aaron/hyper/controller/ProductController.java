package com.aaron.hyper.controller;

import com.aaron.hyper.mapper.ProductMapper;
import com.aaron.hyper.po.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by AARON on 2017/7/15.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/{id}")
    public Product getProductInfo(@PathVariable("id") Long productId) {

        return productMapper.select(productId);
    }

    @PutMapping("/{id}")
    public Product updateProductInfo(@PathVariable("id") Long productId, @RequestBody Product newProduct) {

        return null;
    }
}
