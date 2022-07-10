package com.mulithreading.java.CompletableFuture;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import com.mulithreading.java.completableFuture.ProductServiceUsingCompletableFuture;
import com.mulithreading.java.domain.Product;
import com.mulithreading.java.service.InventoryService;
import com.mulithreading.java.service.ProductInfoService;
import com.mulithreading.java.service.ReviewService;

public class ProductServiceUsingCompletableFutureTest {

	private ProductInfoService productInfoService = new ProductInfoService();
	private ReviewService reviewService = new ReviewService();
	private InventoryService inventoryService = new InventoryService();
	ProductServiceUsingCompletableFuture pscf = new ProductServiceUsingCompletableFuture(productInfoService, reviewService);
	ProductServiceUsingCompletableFuture pscfi = new ProductServiceUsingCompletableFuture(productInfoService, reviewService, inventoryService);
	//	
//	@Test
//	void test_retrieveProductDetails() {
//		Product product = pscf.retrieveProductDetails("ABC123");
//		assertNotNull(product);
//		assertTrue(product.getProductInfo().getProductOptions().size()>0);
//		assertNotNull(product.getReview());
//		
//	}
	
//	@Test
//	void test_retrieveProductDetails_approach2() {
//		CommonUtil.startTimer();
//		CompletableFuture<Product> productCf = pscf.retrieveProductDetails_approach2("ABC123");
//		
//		productCf.thenAccept(product ->{
//		assertNotNull(product);
//		assertTrue(product.getProductInfo().getProductOptions().size()>0);
//		assertNotNull(product.getReview());
//	}).join();
//		CommonUtil.timeTaken();
//	}
	
//	@Test
//	void test_retrieveProductDetailsWithInventory() {
//		
//		Product product = pscfi.retrieveProductDetailsWithInventory("ABC1234");
//		assertNotNull(product);
//		assertTrue(product.getProductInfo().getProductOptions().size()>0);
//		assertNotNull(product.getReview());
//		product.getProductInfo().getProductOptions().forEach(productOption->
//				assertNotNull(productOption.getInventory())
//				);
//	}
	
	@Test
	void test_retrieveProductDetailsWithInventory_approach2() {
		
		Product product = pscfi.retrieveProductDetailsWithInventory_approach2("ABC1234");
		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size()>0);
		assertNotNull(product.getReview());
		product.getProductInfo().getProductOptions().forEach(productOption->
		assertNotNull(productOption.getInventory())
				);
	}

}
