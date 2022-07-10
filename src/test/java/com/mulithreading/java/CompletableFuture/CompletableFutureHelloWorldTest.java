package com.mulithreading.java.CompletableFuture;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import com.mulithreading.java.completableFuture.CompletableFutureHelloWorld;
import com.mulithreading.java.service.HelloWorldService;
import com.mulithreading.java.util.CommonUtil;

public class CompletableFutureHelloWorldTest {
	
	private HelloWorldService hws = new HelloWorldService();
	
	private CompletableFutureHelloWorld cfhw
			 = new CompletableFutureHelloWorld(hws);
//	@Test
//	void test_helloWorld() {
//		 CompletableFuture<String> cf= cfhw.helloWorld();
//		 cf.thenAccept(str-> assertEquals("HELLO WORLD", str)).join();
//	}
//	
//	@Test
//	void test_helloWorld_withSize() {
//		CompletableFuture<String> cf = cfhw.helloWorld_withSize();
//		cf.thenAccept(str-> assertEquals("11 - HELLO WORLD", str)).join();
//	}
	
//	@Test
//	void test_helloworld_multiple_async_calls() {
//		String result = cfhw.helloworld_multiple_async_calls();
//		assertEquals("HELLO WORLD!", result);
//	}
	
//	@Test
	void test_helloworld_three_async_calls() {
		String result = cfhw.helloworld_three_async_calls();
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
//	@Test
	void test_helloworld_three_async_calls_log() {
		String result = cfhw.helloworld_three_async_calls_log();
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
//	@Test
	void test_helloworld_three_async_calls_log_async() {
		String result = cfhw.helloworld_three_async_calls_log_async();
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
//	@Test
	void test_helloworld_three_async_calls_custom_threadpool() {
		String result = cfhw.helloworld_three_async_calls_custom_threadpool();
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
//	@Test
	void test_helloworld_three_async_calls_custom_threadpool_async() {
		String result = cfhw.helloworld_three_async_calls_custom_threadpool_async();
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
//	@Test
	void test_helloWorld_thenCompose() {
		CommonUtil.startTimer();
		cfhw.helloWorld_thenCompose()
		.thenAccept(s->assertEquals("HELLO WORLD!", s))
		.join();
		CommonUtil.timeTaken();
		
	}
	
	@Test
	void test_anyOf() {
		CommonUtil.startTimer();
		String anyOf = cfhw.anyOf();
		assertEquals("hello world", anyOf);
		CommonUtil.timeTaken();
	}
}
