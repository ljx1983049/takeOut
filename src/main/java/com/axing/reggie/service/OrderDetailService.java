package com.axing.reggie.service;

import com.axing.reggie.domain.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    void save(List<OrderDetail> orderDetailList);

    List<OrderDetail> getOrderDetailByOrderId(Long id);
}
