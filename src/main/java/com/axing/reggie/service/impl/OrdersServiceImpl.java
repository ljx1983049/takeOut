package com.axing.reggie.service.impl;

import com.axing.reggie.common.BaseContext;
import com.axing.reggie.domain.*;
import com.axing.reggie.dto.OrdersDto;
import com.axing.reggie.dto.PageDto;
import com.axing.reggie.exception.BusinessException;
import com.axing.reggie.mapper.OrdersMapper;
import com.axing.reggie.service.*;
import com.axing.reggie.vo.PageVo;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 提交订单，将订单数据添加到数据库
     * @param orders
     */
    @Override
    @Transactional
    public void submit(Orders orders) {
        // 完善订单数据
        //生成id和订单，使用雪花
        long id = IdWorker.getId();
        orders.setId(id);
        orders.setNumber(String.valueOf(id));
        orders.setStatus(2);
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        orders.setUserId(userId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        //根据用户id查询购物车信息
        List<ShoppingCart> shoppingCartList = shoppingCartService.getListByUserId(userId);
        if(shoppingCartList == null || shoppingCartList.size()==0){
            throw new BusinessException("购物车为空,不能下单");
        }
        //计算购物车金额，给订单详细列表赋值
        BigDecimal money = new BigDecimal(0);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShoppingCart sc:shoppingCartList) {
            BigDecimal amount = sc.getAmount();//获取商品金额
            Integer number = sc.getNumber();//获取商品数量
            //计算总金额
            BigDecimal _money = amount.multiply(new BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP));//四舍五入保留
            money = money.add(_money);
            //给订单详细表赋值
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setName(sc.getName());
            orderDetail.setImage(sc.getImage());
            orderDetail.setOrderId(id);
            orderDetail.setDishId(sc.getDishId());
            orderDetail.setSetmealId(sc.getSetmealId());
            orderDetail.setDishFlavor(sc.getDishFlavor());
            orderDetail.setNumber(sc.getNumber());
            orderDetail.setAmount(sc.getAmount());
            orderDetailList.add(orderDetail);
        }
        orders.setAmount(money);
        // 根据地址id查询用户收货地址信息
        AddressBook addressBook = addressBookService.getAddressBook(orders.getAddressBookId());
        if (addressBook == null){
            throw new BusinessException("用户没有填写地址");
        }
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getDetail());
        //根据用户id查询用户信息
        User user = userService.getUser(userId);
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());

        //将订单数据保存到订单表
        ordersMapper.save(orders);

        //将订单详细数据添加到订单详细表
        orderDetailService.save(orderDetailList);

        //清空购物车
        shoppingCartService.cleanByUserId(userId);
    }

    /**
     * 用户界面获取订单列表
     * @return
     */
    @Override
    public PageVo<OrdersDto> getUserPage(PageDto pageDto) {
        //获取用户id
        pageDto.setUserId(BaseContext.getCurrentId());
        //根据用户id查询订单列表
        List<OrdersDto> ordersDtoList = ordersMapper.getOrdersByUserId(pageDto);
        //获取订单详细
        for (OrdersDto od:ordersDtoList) {
            //根据订单id获取订单详细列表
            List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrderId(od.getId());
            od.setOrderDetails(orderDetailList);
        }

        PageVo<OrdersDto> vo = new PageVo<>();
        vo.setRecords(ordersDtoList);
        return vo;
    }

    /**
     * 根据订单id查询订单数据
     * @param orders
     * @return
     */
    @Override
    public void getOrdersById(Orders orders) {
        //根据订单id获取订单详细列表
        List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrderId(orders.getId());

        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        //将订单数据添加到购物车
        for (OrderDetail od:orderDetailList) {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setName(od.getName());
            shoppingCart.setImage(od.getImage());
            shoppingCart.setUserId(BaseContext.getCurrentId());
            shoppingCart.setDishId(od.getDishId());
            shoppingCart.setSetmealId(od.getSetmealId());
            shoppingCart.setDishFlavor(od.getDishFlavor());
            shoppingCart.setNumber(od.getNumber());
            shoppingCart.setAmount(od.getAmount());
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartList.add(shoppingCart);
        }
        //将数据批量添加到购物车
        shoppingCartService.addListShoppingCart(shoppingCartList);

    }

    /**
     * 查询所有订单
     * @param pageDto
     * @return
     */
    @Override
    public PageVo<OrdersDto> getPage(PageDto pageDto) {
        // 查询订单列表
        List<OrdersDto> ordersDtoList = ordersMapper.getOrdersByUserId(pageDto);
        //获取订单详细
        for (OrdersDto od:ordersDtoList) {
            //根据订单id获取订单详细列表
            List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrderId(od.getId());
            od.setOrderDetails(orderDetailList);
        }
        //查询总记录条数
        int total = ordersMapper.getTotal(pageDto);

        PageVo<OrdersDto> vo = new PageVo<>();
        vo.setRecords(ordersDtoList);
        vo.setTotal(total);
        return vo;
    }

    /**
     * 后台修改订单状态
     * @param orders
     */
    @Override
    public void updateStatus(Orders orders) {
        ordersMapper.updateOrderStatusById(orders);
    }


}
