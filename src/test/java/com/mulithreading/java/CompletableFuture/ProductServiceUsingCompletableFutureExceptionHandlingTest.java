package com.mulithreading.java.CompletableFuture;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mulithreading.java.completableFuture.ProductServiceUsingCompletableFuture;
import com.mulithreading.java.domain.Product;
import com.mulithreading.java.service.InventoryService;
import com.mulithreading.java.service.ProductInfoService;
import com.mulithreading.java.service.ReviewService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUsingCompletableFutureExceptionHandlingTest {

	@Mock
	private ProductInfoService productInfoServiceMock;
	@Mock
	private ReviewService reviewServiceMock;
	@Mock
	private InventoryService inventoryServiceMock;
	@InjectMocks
	ProductServiceUsingCompletableFuture pscfi;

//	@Test
	void test_retrieveProductDetailsWithInventory_approach2_excpetionHandling() {
		
		
		String productId = "ABC1234";
		when(productInfoServiceMock.retrieveProductInfo(any())).thenCallRealMethod();
		when(inventoryServiceMock.addInventory(any())).thenCallRealMethod();
		when(reviewServiceMock.retrieveReviews(any())).thenThrow(new RuntimeException("Exception Occured for Review Service"));
		Product product = pscfi.retrieveProductDetailsWithInventory_approach2(productId);
		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size()>0);
		assertNotNull(product.getReview());
		assertEquals(0, product.getReview().getNoOfReviews());
		product.getProductInfo().getProductOptions().forEach(productOption->
		assertNotNull(productOption.getInventory())
				);
	}
	
//	@Test
	void test_retrieveProductDetailsWithInventory_approach2_excpetionHandling_for_product_service() {
		
		
		String productId = "ABC1234";
		when(productInfoServiceMock.retrieveProductInfo(any())).thenThrow(new RuntimeException("Exception Occured for Product Service"));
		when(reviewServiceMock.retrieveReviews(any())).thenCallRealMethod();
		Assertions.assertThrows(RuntimeException.class, ()->pscfi.retrieveProductDetailsWithInventory_approach2(productId));
		}
	
	@Test
	void test_retrieveProductDetailsWithInventory_approach2_excpetionHandling_inventory_serivce() {
		
		
		String productId = "ABC1234";
		when(productInfoServiceMock.retrieveProductInfo(any())).thenCallRealMethod();
		when(inventoryServiceMock.addInventory(any())).thenThrow(new RuntimeException("Exception Occured for Inventory Service"));
		when(reviewServiceMock.retrieveReviews(any())).thenCallRealMethod();
		Product product = pscfi.retrieveProductDetailsWithInventory_approach2(productId);
		assertNotNull(product);
		assertTrue(product.getProductInfo().getProductOptions().size()>0);
		assertNotNull(product.getReview());
		product.getProductInfo().getProductOptions().forEach(
				productOption->{
					assertNotNull(productOption.getInventory());
					assertEquals(1, productOption.getInventory().getCount());
				}
				);
	}
}
