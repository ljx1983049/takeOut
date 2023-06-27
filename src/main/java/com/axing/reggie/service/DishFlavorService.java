package com.axing.reggie.service;

import com.axing.reggie.domain.DishFlavor;

import java.util.List;

public interface DishFlavorService {
    int save(List<DishFlavor> flavors);

    /**
     * 根据菜品id获取菜品口味信息列表
     * @param dishId
     * @return
     */
    List<DishFlavor> getFlavorListByDishId(Long dishId);

    /**
     * 根据菜品id删除菜品口味信息
     * @param dishId
     */
    void deleteByDishId(Long dishId);

    /**
     * 根据菜品id批量删除菜品口味信息
     * @param ids
     */
    void deleteByDishIds(List<Long> ids);
}
