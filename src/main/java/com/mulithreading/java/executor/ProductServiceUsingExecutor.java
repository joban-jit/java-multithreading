package com.mulithreading.java.executor;

import static com.mulithreading.java.util.CommonUtil.stopWatch;
import static com.mulithreading.java.util.LoggerUtil.log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.mulithreading.java.domain.Product;
import com.mulithreading.java.domain.ProductInfo;
import com.mulithreading.java.domain.Review;
import com.mulithreading.java.service.ProductInfoService;
import com.mulithreading.java.service.ReviewService;

public class ProductServiceUsingExecutor {
	private static int numberOfThreads = Runtime.getRuntime().availableProcessors();
	private static ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
	private ProductInfoService productInfoService;
	private ReviewService reviewService;

	public ProductServiceUsingExecutor(ProductInfoService productInfoService, ReviewService reviewService) {
		this.productInfoService = productInfoService;
		this.reviewService = reviewService;
	}

	public Product retrieveProductDetails(String productId) throws InterruptedException, ExecutionException, TimeoutException {
		stopWatch.start();

		// using Callable with implemented class

		Callable<ProductInfo> productInfoCallable = new ProductInfoServiceCallable(productId);

		Future<ProductInfo> productInfoFuture = executorService.submit(productInfoCallable);
		// using Callable with anonymous class
//		Future<ProductInfo> productInfoFuture2 = executorService.submit(new Callable<ProductInfo>() {
//
//			@Override
//			public ProductInfo call() throws Exception {
//				return productInfoService.retrieveProductInfo(productId);
//			}
//			
//		});

		//        executorService.submit(()->productInfoService.retrieveProductInfo(productId));

		// using Callalbe as lambda
		Future<Review> reviewFuture = executorService.submit(() -> reviewService.retrieveReviews(productId));
//		ProductInfo productInfo = productInfoFuture.get();
		ProductInfo productInfo = productInfoFuture.get(2, TimeUnit.SECONDS);
		Review review = reviewFuture.get();

		//        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
		//        Review review = reviewService.retrieveReviews(productId); // blocking call
		
		stopWatch.stop();
		log("Total Time Taken : " + stopWatch.getTime());
		return new Product(productId, productInfo, review);
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

		ProductInfoService productInfoService = new ProductInfoService();
		ReviewService reviewService = new ReviewService();
		ProductServiceUsingExecutor productService = new ProductServiceUsingExecutor(productInfoService, reviewService);
		String productId = "ABC123";
		Product product = productService.retrieveProductDetails(productId);
		log("Product is " + product);
		executorService.shutdown();

	}

	public class ProductInfoServiceCallable implements Callable<ProductInfo> {

		private String productId;

		public ProductInfoServiceCallable(String productId) {
			this.productId = productId;
		}

		@Override
		public ProductInfo call() throws Exception {
			return productInfoService.retrieveProductInfo(productId);
		}

	}

}
