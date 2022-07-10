package com.mulithreading.java.CompletableFuture;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mulithreading.java.completableFuture.CompletableFutureHelloWorldExceptionHandling;
import com.mulithreading.java.service.HelloWorldService;

@ExtendWith(MockitoExtension.class)
public class CompletableFutureHelloWorldExceptionHandlingTest {
	
	@Mock
	private HelloWorldService hws = mock(HelloWorldService.class);

	@InjectMocks
	private CompletableFutureHelloWorldExceptionHandling cfhweh;
	
	
	@Test
	void test_helloworld_three_async_calls_handle_method_happy_path() {
		
		when(hws.hello()).thenCallRealMethod();
		when(hws.world()).thenCallRealMethod();
		
		String result = cfhweh.helloworld_three_async_calls_handle_method();
		
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
//	@Test
	void test_helloworld_three_async_calls_handle_method_forHelloCall() {
		
		when(hws.hello()).thenThrow(new RuntimeException("Exception Occured"));
		when(hws.world()).thenCallRealMethod();
		
		String result = cfhweh.helloworld_three_async_calls_handle_method();
		
		assertEquals(" WORLD! HI COMPLETABLEFUTURE", result);
	}
	
//	@Test
	void test_helloworld_three_async_calls_handle_method_forBothCalls() {
		
		when(hws.hello()).thenThrow(new RuntimeException("Exception Occured"));
		when(hws.world()).thenThrow(new RuntimeException("Exception Occured"));
		
		String result = cfhweh.helloworld_three_async_calls_handle_method();
		
		assertEquals(" HI COMPLETABLEFUTURE", result);
	}
	
//	@Test
	void test_helloworld_three_async_calls_exceptionally_method_happy_path() {
		
		when(hws.hello()).thenCallRealMethod();
		when(hws.world()).thenCallRealMethod();
		
		String result = cfhweh.helloworld_three_async_calls_exceptionally_method();
		
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
//	@Test
	void test_helloworld_three_async_calls_exceptionally_method_forHelloCall() {
		
		when(hws.hello()).thenThrow(new RuntimeException("Exception Occured"));
		when(hws.world()).thenCallRealMethod();
		
		String result = cfhweh.helloworld_three_async_calls_exceptionally_method();
		
		assertEquals(" WORLD! HI COMPLETABLEFUTURE", result);
	}
	
//	@Test
	void test_helloworld_three_async_calls_exceptionally_method_forBothCalls() {
		
		when(hws.hello()).thenThrow(new RuntimeException("Exception Occured"));
		when(hws.world()).thenThrow(new RuntimeException("Exception Occured"));
		
		String result = cfhweh.helloworld_three_async_calls_exceptionally_method();
		
		assertEquals(" HI COMPLETABLEFUTURE", result);
	}
//	@Test
	void test_helloworld_three_async_calls_when_complete_method_happy_path() {
		
		when(hws.hello()).thenCallRealMethod();
		when(hws.world()).thenCallRealMethod();
		
		String result = cfhweh.helloworld_three_async_calls_when_complete_method();
		
		assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE", result);
	}
	
//	@Test
	void test_helloworld_three_async_calls_when_complete_method_forHelloCall() {
		
		when(hws.hello()).thenThrow(new RuntimeException("Exception Occured1"));
		when(hws.world()).thenCallRealMethod();
		
		String result = cfhweh.helloworld_three_async_calls_when_complete_method();
		
		assertEquals(" HI COMPLETABLEFUTURE", result);
	}
	
//	@Test
	void test_helloworld_three_async_calls_when_complete_method_forWorldCalls() {
		
		when(hws.hello()).thenCallRealMethod();
		when(hws.world()).thenThrow(new RuntimeException("Exception Occured2"));
		
		String result = cfhweh.helloworld_three_async_calls_when_complete_method();
		
		assertEquals(" HI COMPLETABLEFUTURE", result);
	}
}
