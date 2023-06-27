package com.axing.reggie.dto;

import com.axing.reggie.domain.OrderDetail;
import com.axing.reggie.domain.Orders;
import lombok.Data;

import java.util.List;

@Data
public class OrdersDto extends Orders {

    private List<OrderDetail> orderDetails;
	
}
