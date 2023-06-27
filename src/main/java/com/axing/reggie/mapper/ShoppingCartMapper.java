package com.axing.reggie.mapper;

import com.axing.reggie.domain.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

    /**
     * 根据 用户id 和 菜品id或者套餐id 查询购物车信息
     * @param shoppingCart
     * @return
     */
    ShoppingCart getShoppingCartByUserIdAndDishIdOrSetmealId(ShoppingCart shoppingCart);

    /**
     * 修改购物车数量
     * @param sc
     */
    void updateShoppingCartNumber(ShoppingCart sc);

    /**
     * 添加购物车
     * @param shoppingCart
     */
    void saveShoppingCart(ShoppingCart shoppingCart);

    /**
     * 根据当前用户id查出购物车信息列表
     * @param userId
     * @return
     */
    List<ShoppingCart> getListByUserId(Long userId);

    /**
     * 根据当前用户id清空购物车
     * @param userId
     */
    void cleanByUserId(Long userId);

    /**
     * 根据用户id和 商品id或套餐id 减少购物车商品数量
     * @param shoppingCart
     */
    void subByUserIdAndDishIyOrSermealId(ShoppingCart shoppingCart);

    /**
     * 根据id删除购物车商品信息
     * @param id
     */
    void deleteShoppingCartById(Long id);

    /**
     * 批量添加购物车信息，对于点击再来一单
     * @param shoppingCartList
     */
    void addListShoppingCart(List<ShoppingCart> shoppingCartList);
}
