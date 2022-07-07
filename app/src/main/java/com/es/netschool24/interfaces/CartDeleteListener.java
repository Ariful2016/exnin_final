package com.es.netschool24.interfaces;

import com.es.netschool24.Models.Cart;

@FunctionalInterface
public interface CartDeleteListener {
    void deleteCart(Cart cart);

}
