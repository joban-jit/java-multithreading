package com.mulithreading.java.completableFuture;

import com.mulithreading.java.domain.Inventory;
import com.mulithreading.java.domain.Product;
import com.mulithreading.java.domain.ProductInfo;
import com.mulithreading.java.domain.ProductOption;
import com.mulithreading.java.domain.Review;
import com.mulithreading.java.service.InventoryService;
import com.mulithreading.java.service.ProductInfoService;
import com.mulithreading.java.service.ReviewService;
import com.mulithreading.java.util.LoggerUtil;

import static com.mulithreading.java.util.CommonUtil.*;
import static com.mulithreading.java.util.LoggerUtil.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ProductServiceUsingCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
       
    }

    public ProductServiceUsingCompletableFuture(
		ProductInfoService productInfoService, ReviewService reviewService,
		InventoryService inventoryService) {
	this.productInfoService = productInfoService;
	this.reviewService = reviewService;
	this.inventoryService = inventoryService;
}
public Product retrieveProductDetails(String productId) {
        stopWatch.start();

//        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
//        Review review = reviewService.retrieveReviews(productId); // blocking call

        CompletableFuture<ProductInfo> productInfoCf = CompletableFuture.supplyAsync(()->productInfoService.retrieveProductInfo(productId));
        
        CompletableFuture<Review> reviewCf = CompletableFuture.supplyAsync(()->reviewService.retrieveReviews(productId));
        
        Product product = productInfoCf
        		.thenCombine(reviewCf, (productInfo, review) -> new Product(productId, productInfo, review))
        		.join();
        
        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }
    
    public CompletableFuture<Product> retrieveProductDetails_approach2(String productId) {
    	
    	CompletableFuture<ProductInfo> productInfoCf = CompletableFuture.supplyAsync(()->productInfoService.retrieveProductInfo(productId));
    	
    	CompletableFuture<Review> reviewCf = CompletableFuture.supplyAsync(()->reviewService.retrieveReviews(productId));
    	
    	return productInfoCf
    			.thenCombine(reviewCf, (productInfo, review) -> new Product(productId, productInfo, review));
    }
    
    public Product retrieveProductDetailsWithInventory(String productId) {
    	 stopWatch.start();
    	CompletableFuture<ProductInfo> productInfoCf = CompletableFuture.supplyAsync(()->{
    		return productInfoService.retrieveProductInfo(productId);
    	})
    	.thenApply(productInfo-> {
    		List<ProductOption> productOptions = updateInventory(productInfo);
			productInfo.setProductOptions(productOptions);
			return productInfo;
    	});
    	CompletableFuture<Review> reviewCf = CompletableFuture.supplyAsync(()->{
    		return reviewService.retrieveReviews(productId);
    	});
    	
    	Product product = productInfoCf
    			.thenCombine(reviewCf,(productInfo, review)-> new Product(productId, productInfo, review))
    			.join();
    	
    	
    	
    	 stopWatch.stop();
         log("Total Time Taken : "+ stopWatch.getTime());
         return product;
    }
    
    
    public Product retrieveProductDetailsWithInventory_approach2(String productId) {
    	stopWatch.start();
    	CompletableFuture<ProductInfo> productInfoCf = CompletableFuture.supplyAsync(()->{
    		return productInfoService.retrieveProductInfo(productId);
    	})
    			.thenApply(productInfo-> {
    				List<ProductOption> productOptions = updateInventory_approach2(productInfo);
    				productInfo.setProductOptions(productOptions);
    				return productInfo;
    			})
    			.whenComplete((productResult, e)->{
    				if(Optional.ofNullable(e).isPresent())
    					log("Inside WhenComplete : "+productResult+" and the exception is : "+e.getMessage());
    			});
    	CompletableFuture<Review> reviewCf = CompletableFuture.supplyAsync(()->{
    		return reviewService.retrieveReviews(productId);
    	})
    	.exceptionally(e->{
    		LoggerUtil.log("Handled the exception in reviewServic"+e.getMessage());
    		return Review.builder()
    				.noOfReviews(0)
    				.overallRating(0.0)
    				.build();
    	});
    	
    	Product product = productInfoCf
    			.thenCombine(reviewCf,(productInfo, review)-> new Product(productId, productInfo, review))
    			.join();
    	
    	
    	
    	stopWatch.stop();
    	log("Total Time Taken : "+ stopWatch.getTime());
    	return product;
    }
    
    
    private List<ProductOption> updateInventory(ProductInfo productInfo) {
    	return productInfo.getProductOptions()
    				.stream()
    				.map(productOption-> {
    					
    					Inventory inventory = inventoryService.addInventory(productOption);
    					productOption.setInventory(inventory);
    					return productOption;
    					}).collect(Collectors.toList());
    }
    
    private List<ProductOption> updateInventory_approach2(ProductInfo productInfo) {
    	 List<CompletableFuture<ProductOption>> productOptionList = productInfo.getProductOptions()
    			.stream()
    			.map(productOption-> {
    				return CompletableFuture.supplyAsync(()->inventoryService.addInventory(productOption))
    								 .exceptionally(e->{
    									 log("Exception in InventoryService : "+ e.getMessage());
    									 return Inventory.builder().count(1).build();
    								 })
    								 .thenApply(inventory -> {
    									 productOption.setInventory(inventory);
    									 return productOption;
    									 });
    								 
    				
    			}).collect(Collectors.toList());
    	 return productOptionList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
}
