package com.axing.reggie.mapper;

import com.axing.reggie.domain.Orders;
import com.axing.reggie.dto.OrdersDto;
import com.axing.reggie.dto.PageDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    /**
     * 添加订单数据
     * @param orders
     */
    void save(Orders orders);

    /**
     * 根据用户id查询订单列表
     * @param pageDto
     * @return
     */
    List<OrdersDto> getOrdersByUserId(PageDto pageDto);

    /**
     * 根据订单id查询订单
     * @param orders
     * @return
     */
    OrdersDto getOrdersById(Orders orders);

    /**
     * 查询订单总条数
     * @param pageDto
     * @return
     */
    int getTotal(PageDto pageDto);

    /**
     * 根据订单id修改订单状态
     * @param orders
     */
    void updateOrderStatusById(Orders orders);
}
