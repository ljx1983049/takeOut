package com.axing.reggie.mapper;

import com.axing.reggie.domain.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    /**
     * 批量添加数据
     * @param orderDetailList
     */
    void save(List<OrderDetail> orderDetailList);

    List<OrderDetail> getOrderDetailByOrderId(Long orderId);
}
