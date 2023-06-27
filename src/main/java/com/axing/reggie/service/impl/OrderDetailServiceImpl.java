package com.axing.reggie.service.impl;

import com.axing.reggie.domain.OrderDetail;
import com.axing.reggie.mapper.OrderDetailMapper;
import com.axing.reggie.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 添加数据
     * @param orderDetailList
     */
    @Override
    public void save(List<OrderDetail> orderDetailList) {
        orderDetailMapper.save(orderDetailList);
    }

    /**
     * 根据订单id获取订单详细列表
     * @param orderId
     * @return
     */
    @Override
    public List<OrderDetail> getOrderDetailByOrderId(Long orderId) {
        return orderDetailMapper.getOrderDetailByOrderId(orderId);
    }
}
