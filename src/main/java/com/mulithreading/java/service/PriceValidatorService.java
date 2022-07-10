package com.mulithreading.java.service;


import com.mulithreading.java.domain.checkout.CartItem;
import com.mulithreading.java.util.LoggerUtil;

import static com.mulithreading.java.util.CommonUtil.*;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem){
        int cartId = cartItem.getItemId();
        LoggerUtil.log("IsCartItemValid: "+cartItem);
        delay(500);
        if (cartId == 7 || cartId == 9 || cartId == 11) {
            return true;
        }
        return false;
    }
}
