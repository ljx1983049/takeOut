package com.axing.reggie.service.impl;

import com.axing.reggie.domain.ShoppingCart;
import com.axing.reggie.mapper.ShoppingCartMapper;
import com.axing.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @Override
    public ShoppingCart addShoppingCart(ShoppingCart shoppingCart) {

        //判断是否是第一次添加
        //根据 用户id 和 菜品id或者套餐id 查询购物车信息
        ShoppingCart sc = shoppingCartMapper.getShoppingCartByUserIdAndDishIdOrSetmealId(shoppingCart);
        if (sc != null){
            //不是第一次添加
            //将数量加一
            //获取当前购物车数量
            Integer number = sc.getNumber();
            //修改购物车表的数量字段
            sc.setNumber(number+1);
            shoppingCartMapper.updateShoppingCartNumber(sc);
        }else {
            //第一次添加
            //设置默认数量为1
            shoppingCart.setNumber(1);
            //设置创建时间
            shoppingCart.setCreateTime(LocalDateTime.now());
            //将提交添加购物车
            shoppingCartMapper.saveShoppingCart(shoppingCart);
            sc = shoppingCart;
        }
        return sc;
    }

    /**
     * 根据当前用户id查出购物车信息列表
     * @param userId
     * @return
     */
    @Override
    public List<ShoppingCart> getListByUserId(Long userId) {
        return shoppingCartMapper.getListByUserId(userId);
    }

    /**
     * 根据当前用户id清空购物车
     * @param userId
     */
    @Override
    public void cleanByUserId(Long userId) {
        shoppingCartMapper.cleanByUserId(userId);
    }

    /**
     * 根据用户id和 商品id或套餐id 减少购物车商品数量
     * @param shoppingCart
     */
    @Override
    @Transactional
    public ShoppingCart sub(ShoppingCart shoppingCart) {
        shoppingCartMapper.subByUserIdAndDishIyOrSermealId(shoppingCart);
        //根据 用户id 和 菜品id或者套餐id 查询购物车信息
        ShoppingCart sc = shoppingCartMapper.getShoppingCartByUserIdAndDishIdOrSetmealId(shoppingCart);
        //查询剩余套餐是否小于1
        if (sc.getNumber()<1){
            //小于1则删除当前购物车商品信息
            shoppingCartMapper.deleteShoppingCartById(sc.getId());
        }
        return sc;
    }

    @Override
    public void addListShoppingCart(List<ShoppingCart> shoppingCartList) {
        shoppingCartMapper.addListShoppingCart(shoppingCartList);
    }
}
