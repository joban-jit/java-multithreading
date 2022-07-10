package com.mulithreading.java.thread;

import static com.mulithreading.java.util.CommonUtil.stopWatch;
import static com.mulithreading.java.util.LoggerUtil.log;

import com.mulithreading.java.domain.Product;
import com.mulithreading.java.domain.ProductInfo;
import com.mulithreading.java.domain.Review;
import com.mulithreading.java.service.ProductInfoService;
import com.mulithreading.java.service.ReviewService;

public class ProductServiceUsingThread {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingThread(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();

        ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
        Thread productInfoThread = new Thread(productInfoRunnable);
        
        ReviewRunnable reviewRunnable = new ReviewRunnable(productId);
        Thread reviewThread = new Thread(reviewRunnable);
        
        productInfoThread.start();
        reviewThread.start();
        
        productInfoThread.join();
        reviewThread.join();
        
        ProductInfo productInfo = productInfoRunnable.getProductInfo();
        Review review = reviewRunnable.getReview();
        
//        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
//        Review review = reviewService.retrieveReviews(productId); // blocking call

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingThread productService = new ProductServiceUsingThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
    
    class ProductInfoRunnable implements Runnable {
    	private ProductInfo productInfo;
    	private String productId;
    	
    	public ProductInfoRunnable(String productId) {
    		 this.productId = productId;
		}
    	
    	public ProductInfo getProductInfo() {
			return productInfo;
		}

		@Override
    	public void run() {
    		productInfo = productInfoService.retrieveProductInfo(productId);
    	}
    }
    
    class ReviewRunnable implements Runnable{
    	
    	private String productId;
    	private Review review;
    	

		public Review getReview() {
			return review;
		}

		public ReviewRunnable(String productId) {
			this.productId = productId;
		}

		@Override
		public void run() {
			review = reviewService.retrieveReviews(productId);
			
		}
    	
    }
}
