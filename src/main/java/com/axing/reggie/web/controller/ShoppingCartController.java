package com.axing.reggie.web.controller;

import com.axing.reggie.common.BaseContext;
import com.axing.reggie.common.R;
import com.axing.reggie.domain.ShoppingCart;
import com.axing.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){

        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        ShoppingCart sc= shoppingCartService.addShoppingCart(shoppingCart);
        return R.success(sc);

    }

    /**
     * 展示购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCartList = shoppingCartService.getListByUserId(userId);
        return R.success(shoppingCartList);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        Long userId = BaseContext.getCurrentId();
        shoppingCartService.cleanByUserId(userId);
        return R.success("清空购物车成功");
    }

    /**
     * 减少购物车商品数量
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        //获取当前用户id
        shoppingCart.setUserId(BaseContext.getCurrentId());
        ShoppingCart sc = shoppingCartService.sub(shoppingCart);
        return R.success(sc);
    }






}
