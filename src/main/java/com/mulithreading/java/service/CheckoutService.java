package com.mulithreading.java.service;

import org.apache.commons.collections4.CollectionUtils;

import com.mulithreading.java.domain.checkout.Cart;
import com.mulithreading.java.domain.checkout.CartItem;
import com.mulithreading.java.domain.checkout.CheckoutResponse;
import com.mulithreading.java.domain.checkout.CheckoutStatus;
import com.mulithreading.java.util.LoggerUtil;

import static com.mulithreading.java.util.CommonUtil.*;

import java.util.List;
import java.util.stream.Collectors;
public class CheckoutService {

	private PriceValidatorService priceValidatorService;
	
	public CheckoutService(PriceValidatorService priceValidatorService) {
		super();
		this.priceValidatorService = priceValidatorService;
	}



	public CheckoutResponse checkout(Cart cart) {
		startTimer();
		List<CartItem> expiredItemsList = cart.getCartItemList()
				.parallelStream()
				.map(cartItem->{
					boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
					cartItem.setExpired(isPriceInvalid);
					return cartItem;
				})
				.filter(CartItem::isExpired)
				.collect(Collectors.toList());
		
		if(CollectionUtils.isNotEmpty(expiredItemsList)) {
			timeTaken();
			return new CheckoutResponse(CheckoutStatus.FAILURE,expiredItemsList);
		}
		
//		double finalPrice = calculateFinalPrice(cart);
		double finalPrice = calculateFinalPrice_with_reduce(cart);
		LoggerUtil.log("Checkout complete. FinalPrice: "+finalPrice);
		timeTaken();
		return new CheckoutResponse(CheckoutStatus.SUCCESS, finalPrice);
				
	}



	private Double calculateFinalPrice(Cart cart) {
//		return cart.getCartItemList()
//			.parallelStream()
//			.map(cartItem -> cartItem.getQuantity()*cartItem.getRate())
//			.collect(Collectors.summingDouble(Double::doubleValue));
		
		// or 
		
		return cart.getCartItemList()
			.parallelStream()
			.map(cartItem -> cartItem.getQuantity()*cartItem.getRate())
			.mapToDouble(Double::doubleValue)
			.sum();
	}
	
	private Double calculateFinalPrice_with_reduce(Cart cart) {
		return cart.getCartItemList()
			.parallelStream()
			.map(cartItem -> cartItem.getQuantity()*cartItem.getRate())
			.reduce(0.0,(a,b)->a+b);
		
		// or 
		
//		return cart.getCartItemList()
//				.parallelStream()
//				.map(cartItem -> cartItem.getQuantity()*cartItem.getRate())
//				.reduce(0.0,Double::sum);
		
		
		
	}
}
