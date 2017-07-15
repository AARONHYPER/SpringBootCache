package com.aaron.hyper.controller;

import com.aaron.hyper.pmapper.Product;
import org.springframework.web.bind.annotation.*;

/**
 * Created by AARON on 2017/7/15.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/{id}")
    public Product getProductInfo(@PathVariable("id") Long productId) {

        return null;
    }

    @PutMapping("/{id}")
    public Product updateProductInfo(@PathVariable("id") Long productId, @RequestBody Product newProduct) {

        return null;
    }
}
