package com.mulithreading.java.completableFuture;

import com.mulithreading.java.service.HelloWorldService;
import com.mulithreading.java.util.CommonUtil;
import com.mulithreading.java.util.LoggerUtil;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelloWorldExceptionHandling {

	private HelloWorldService hws;

	public CompletableFutureHelloWorldExceptionHandling(HelloWorldService hws) {
		super();
		this.hws = hws;
	}
	
	public String helloworld_three_async_calls_handle_method() {
		CommonUtil.startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
		CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> hws.world());
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
			CommonUtil.delay(1000);
			return " Hi CompletableFuture";
		});
		
		String result = hello
				.handle((res, e)->{
					LoggerUtil.log("res of hello is : "+res);
					if(Optional.ofNullable(e).isPresent()) {
						LoggerUtil.log("Exception after hello is : "+e.getMessage());
						return "";
					}else {
						return res;
					}
					
				})
				.thenCombine(world, (h,w)-> h+w)//h is hello result, w is world result
				.handle((res, e)->{
					LoggerUtil.log("res of hello is : "+res);
					if(Optional.ofNullable(e).isPresent()) {
						LoggerUtil.log("Exception after world is : "+e.getMessage());
						return "";
					}else {
						return res;
					}
					
				})
				.thenCombine(hiCompletableFuture, (previous, current)-> previous+current)// previous is previous execution result and current is current execution result which would be hiCompletableFuture
				.thenApply(String::toUpperCase)
				.join();
		CommonUtil.timeTaken();
		return result;
		
	}


public String helloworld_three_async_calls_exceptionally_method() {
	CommonUtil.startTimer();
	CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
	CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> hws.world());
	CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
		CommonUtil.delay(1000);
		return " Hi CompletableFuture";
	});
	
	String result = hello
			.exceptionally(e->{
					LoggerUtil.log("Exception after hello is : "+e.getMessage());
					return "";
				
			})
			.thenCombine(world, (h,w)-> h+w)//h is hello result, w is world result
			.exceptionally(e->{
					LoggerUtil.log("Exception after world is : "+e.getMessage());
					return "";
				
			})
			.thenCombine(hiCompletableFuture, (previous, current)-> previous+current)// previous is previous execution result and current is current execution result which would be hiCompletableFuture
			.thenApply(String::toUpperCase)
			.join();
	CommonUtil.timeTaken();
	return result;
	
}



public String helloworld_three_async_calls_when_complete_method() {
	CommonUtil.startTimer();
	CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
	CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> hws.world());
	CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
		CommonUtil.delay(1000);
		return " Hi CompletableFuture";
	});
	
	String result = hello
			.whenComplete((res, e)->{
				LoggerUtil.log("res of hello is : "+res);
				if(Optional.ofNullable(e).isPresent()) {
					LoggerUtil.log("Exception after hello is : "+e.getMessage());
				}
				
			})
			.thenCombine(world, (h,w)-> h+w)//h is hello result, w is world result
			.whenComplete((res, e)->{
				LoggerUtil.log("res of world is : "+res);
				if(Optional.ofNullable(e).isPresent()) {
					LoggerUtil.log("Exception after world is : "+e.getMessage());
				}
				
			})
			.exceptionally(e->{
				LoggerUtil.log("Exception after hello and world block is : "+e.getMessage());
				return "";
			})
			.thenCombine(hiCompletableFuture, (previous, current)-> previous+current)// previous is previous execution result and current is current execution result which would be hiCompletableFuture
			.thenApply(String::toUpperCase)
			.join();
	CommonUtil.timeTaken();
	return result;
	
}

}

