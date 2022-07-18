package com.exnin.onlinelearning.interfaces;

import com.exnin.onlinelearning.Models.Cart;

@FunctionalInterface
public interface CartDeleteListener {
    void deleteCart(Cart cart);

}
