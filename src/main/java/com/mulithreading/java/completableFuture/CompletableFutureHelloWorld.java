package com.mulithreading.java.completableFuture;

import com.mulithreading.java.service.HelloWorldService;
import com.mulithreading.java.util.CommonUtil;
import com.mulithreading.java.util.LoggerUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureHelloWorld {

	private HelloWorldService hws;
	
	
	public CompletableFutureHelloWorld(HelloWorldService hws) {
		this.hws = hws;
	}

	public static void main(String[] args) {
		
//		CompletableFuture
//			.supplyAsync(hws::helloWorld)
////			.supplyAsync(()->hws.helloWorld())
//			.thenApply(String::toUpperCase)
//	//		.thenApply(result -> result.toUpperCase())
//			.thenAccept(result-> log("result: "+result))
//			.join();// to hold the main thread
//		
//		log("Done");
////		delay(2000);

	}
	
	public String anyOf() {
		// db
		 CompletableFuture<String> dbCf = CompletableFuture.supplyAsync(()->{
			CommonUtil.delay(5000);
			LoggerUtil.log("response from db");
			return "hello world";
		});
		
		// rest call
		 CompletableFuture<String> restCf = CompletableFuture.supplyAsync(()->{
				CommonUtil.delay(2000);
				LoggerUtil.log("response from rest");
				return "hello world";
			});
		// soap call
		 CompletableFuture<String> soapCf = CompletableFuture.supplyAsync(()->{
				CommonUtil.delay(3000);
				LoggerUtil.log("response from soap");
				return "hello world";
			});
		 
		 List<CompletableFuture<String>> cfList = List.of(dbCf, restCf, soapCf);
		 CompletableFuture<Object> anyOfResult = CompletableFuture.anyOf(cfList.toArray(new CompletableFuture[cfList.size()]));
	
		String result =  (String) anyOfResult.thenApply(v->{
			 if(v instanceof String) {
				 return v;
			 }
			 return null;
		 }).join();
		
		return result;
	}
	
	public CompletableFuture<String> helloWorld(){
		hws = new HelloWorldService();
		return CompletableFuture
				.supplyAsync(hws::helloWorld)
				.thenApply(String::toUpperCase);
	}

	
	public CompletableFuture<String> helloWorld_withSize(){
		hws = new HelloWorldService();
		return CompletableFuture
				.supplyAsync(hws::helloWorld)
				.thenApply(String::toUpperCase)
				.thenApply(s-> s.length()+ " - "+s);
	}
	
	public String helloworld_multiple_async_calls() {
		hws = new HelloWorldService();
		CommonUtil.startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> hws.world());
		
		String result = hello.thenCombine(world, (h,w)-> h+w)//h is hello result, w is world result
			  .thenApply(String::toUpperCase)
			  .join();
		CommonUtil.timeTaken();
		return result;
		
	}
	public String helloworld_three_async_calls() {
		hws = new HelloWorldService();
		CommonUtil.startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
			CommonUtil.delay(1000);
			return " Hi CompletableFuture";
		});
		
		String result = hello
				.thenCombine(world, (h,w)-> h+w)//h is hello result, w is world result
				.thenCombine(hiCompletableFuture, (previous, current)-> previous+current)// previous is previous execution result and current is current execution result which would be hiCompletableFuture
				.thenApply(String::toUpperCase)
				.join();
		CommonUtil.timeTaken();
		return result;
		
	}
	
	public String helloworld_three_async_calls_log() {
		hws = new HelloWorldService();
		CommonUtil.startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
			CommonUtil.delay(1000);
			return " Hi CompletableFuture";
		});
		
		String result = hello
				.thenCombine(world, (h,w)-> {
					LoggerUtil.log("thenCombile h/w");
					return h+w;
					})//h is hello result, w is world result
				.thenCombine(hiCompletableFuture, (previous, current)-> {
					LoggerUtil.log("thenCombile h/w/hi");
					 return previous+current;
					})// previous is previous execution result and current is current execution result which would be hiCompletableFuture
				.thenApply(s->{
					LoggerUtil.log("thenApply h/w/hi to UPPERCASE");
					return s.toUpperCase();
				})
				.join();
		CommonUtil.timeTaken();
		return result;
		
	}
	
	public String helloworld_three_async_calls_log_async() {
		hws = new HelloWorldService();
		CommonUtil.startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
			CommonUtil.delay(1000);
			return " Hi CompletableFuture";
		});
		
		String result = hello
				.thenCombineAsync(world, (h,w)-> {
					LoggerUtil.log("thenCombile h/w");
					return h+w;
				})//h is hello result, w is world result
				.thenCombineAsync(hiCompletableFuture, (previous, current)-> {
					LoggerUtil.log("thenCombile h/w/hi");
					return previous+current;
				})// previous is previous execution result and current is current execution result which would be hiCompletableFuture
				.thenApplyAsync(s->{
					LoggerUtil.log("thenApply h/w/hi to UPPERCASE");
					return s.toUpperCase();
				})
				.join();
		CommonUtil.timeTaken();
		return result;
		
	}
	public String helloworld_three_async_calls_custom_threadpool() {
		hws = new HelloWorldService();
		CommonUtil.startTimer();
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello(),executorService);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> hws.world(),executorService);
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
			CommonUtil.delay(1000);
			LoggerUtil.log("inside hiComplatableFuture");
			return " Hi CompletableFuture";
		},executorService);
		
		String result = hello
				.thenCombine(world, (h,w)-> {
					LoggerUtil.log("thenCombile h/w");
					return h+w;
				})//h is hello result, w is world result
				.thenCombine(hiCompletableFuture, (previous, current)-> {
					LoggerUtil.log("thenCombile h/w/hi");
					return previous+current;
				})// previous is previous execution result and current is current execution result which would be hiCompletableFuture
				.thenApply(s->{
					LoggerUtil.log("thenApply h/w/hi to UPPERCASE");
					return s.toUpperCase();
				})
				.join();
		CommonUtil.timeTaken();
		return result;
		
	}
	public String helloworld_three_async_calls_custom_threadpool_async() {
//		hws = new HelloWorldService();
		CommonUtil.startTimer();
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello(),executorService);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> hws.world(),executorService);
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
			CommonUtil.delay(1000);
			LoggerUtil.log("inside hiComplatableFuture");
			return " Hi CompletableFuture";
		},executorService);
		
		String result = hello
				.thenCombineAsync(world, (h,w)-> {
					LoggerUtil.log("thenCombile h/w");
					return h+w;
				}, executorService)//h is hello result, w is world result
				.thenCombineAsync(hiCompletableFuture, (previous, current)-> {
					LoggerUtil.log("thenCombile h/w/hi");
					return previous+current;
				}, executorService)// previous is previous execution result and current is current execution result which would be hiCompletableFuture
				.thenApplyAsync(s->{
					LoggerUtil.log("thenApply h/w/hi to UPPERCASE");
					return s.toUpperCase();
				}, executorService)
				.join();
		CommonUtil.timeTaken();
		return result;
		
	}
	
	public CompletableFuture<String> helloWorld_thenCompose(){
		hws = new HelloWorldService();
		CompletableFuture<String> result = CompletableFuture.supplyAsync(hws::hello)
			.thenCompose(previous-> hws.worldFuture(previous))
			.thenApply(String::toUpperCase);
		return result;
	}
}
