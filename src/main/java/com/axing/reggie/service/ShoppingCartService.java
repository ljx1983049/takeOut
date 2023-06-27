package com.axing.reggie.service;

import com.axing.reggie.domain.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart addShoppingCart(ShoppingCart shoppingCart);

    List<ShoppingCart> getListByUserId(Long userId);

    void cleanByUserId(Long userId);

    ShoppingCart sub(ShoppingCart shoppingCart);

    /**
     * 批量添加购物车信息，对于点击再来一单
     * @param shoppingCartList
     */
    void addListShoppingCart(List<ShoppingCart> shoppingCartList);
}
