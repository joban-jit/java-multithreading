package com.mulithreading.java.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.mulithreading.java.domain.checkout.Cart;
import com.mulithreading.java.domain.checkout.CheckoutResponse;
import com.mulithreading.java.domain.checkout.CheckoutStatus;
import com.mulithreading.java.util.DataSet;

public class CheckoutServiceTest {

	private PriceValidatorService priceValidatorService = new PriceValidatorService();;
	private CheckoutService checkoutService = new CheckoutService(priceValidatorService);
	
	@Before
	public void stepUp() {
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "50");
	}
	
	@Test
	void checkout_6_items() {
		Cart cart = DataSet.createCart(6);
		CheckoutResponse checkout = checkoutService.checkout(cart );
		assertEquals(CheckoutStatus.SUCCESS ,checkout.getCheckoutStatus());
		assertTrue( checkout.getFinalRate()>0);
	}
	

	@Test
	void checkout_13_items() {
		Cart cart = DataSet.createCart(13);
		CheckoutResponse checkout = checkoutService.checkout(cart );
		assertEquals(CheckoutStatus.FAILURE ,checkout.getCheckoutStatus());
	}
	
	@Test
	void modify_parallelism() {
		
		Cart cart = DataSet.createCart(50);
		CheckoutResponse checkout = checkoutService.checkout(cart);
//		System.out.println("Parallelism: "+ForkJoinPool.getCommonPoolParallelism());
		assertEquals(CheckoutStatus.FAILURE ,checkout.getCheckoutStatus());
	}
	
	
	
	
}
