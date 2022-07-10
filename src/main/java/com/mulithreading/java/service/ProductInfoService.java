package com.mulithreading.java.service;

import static com.mulithreading.java.util.CommonUtil.delay;

import java.util.List;

import com.mulithreading.java.domain.ProductInfo;
import com.mulithreading.java.domain.ProductOption;

public class ProductInfoService {

    public ProductInfo retrieveProductInfo(String productId) {
        delay(1000);
        List<ProductOption> productOptions = List.of(new ProductOption(1, "64GB", "Black", 699.99),
                new ProductOption(2, "128GB", "Black", 749.99));
        return ProductInfo.builder().productId(productId)
                .productOptions(productOptions)
                .build();
    }
}
