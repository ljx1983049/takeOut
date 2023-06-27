package com.axing.reggie.service;

import com.axing.reggie.domain.Orders;
import com.axing.reggie.dto.OrdersDto;
import com.axing.reggie.dto.PageDto;
import com.axing.reggie.vo.PageVo;

import java.util.List;

public interface OrdersService {
    void submit(Orders orders);

    PageVo<OrdersDto> getUserPage(PageDto pageDto);

    void getOrdersById(Orders orders);

    PageVo<OrdersDto> getPage(PageDto pageDto);

    void updateStatus(Orders orders);
}
