package com.axing.reggie.web.controller;

import com.axing.reggie.common.R;
import com.axing.reggie.domain.Orders;
import com.axing.reggie.dto.OrdersDto;
import com.axing.reggie.dto.PageDto;
import com.axing.reggie.service.OrdersService;
import com.axing.reggie.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 提交订单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 用户端获取订单列表
     * @param pageDto
     * @return
     */
    @GetMapping("/userPage")
    public R<PageVo<OrdersDto>> userPage(PageDto pageDto){
        PageVo<OrdersDto> vo = ordersService.getUserPage(pageDto);
        if (vo.getRecords() != null && vo.getRecords().size()>0){
            return R.success(vo);
        }
        return R.error("没有查询到订单");
    }

    /**
     * 用户端点击再来一单
     * @param orders
     * @return
     */
    @PostMapping("/again")
    public R<String> userPage(@RequestBody Orders orders){
        ordersService.getOrdersById(orders);
        return R.success("再来一单成功");
    }

    /**
     * 后台获取订单列表
     * @param pageDto
     * @return
     */
    @GetMapping("/page")
    public R<PageVo<OrdersDto>> page(PageDto pageDto){
        PageVo<OrdersDto> vo = ordersService.getPage(pageDto);
        return R.success(vo);
    }

    /**
     * 后台修改订单状态
     * @param orders
     * @return
     */
    @PutMapping
    public R<String> orderStatus(@RequestBody Orders orders){
        ordersService.updateStatus(orders);
        return R.success("修改订单状态成功");
    }

}
