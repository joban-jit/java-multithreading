package com.mulithreading.java.service;

import com.mulithreading.java.domain.Inventory;
import com.mulithreading.java.domain.ProductOption;

import static com.mulithreading.java.util.CommonUtil.*;

public class InventoryService {
    public Inventory addInventory(ProductOption productOption) {
        delay(500);
        return Inventory.builder()
                .count(2).build();

    }

//    public CompletableFuture<Inventory> addInventory_CF(ProductOption productOption) {
//
//        return CompletableFuture.supplyAsync(() -> {
//            delay(500);
//            return Inventory.builder()
//                    .count(2).build();
//        });
//
//    }
}
