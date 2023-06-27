package com.axing.reggie.mapper;

import com.axing.reggie.domain.DishFlavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

    //保存菜品口味，支持多条添加
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
    void deleteFlavorById(Long dishId);

    /**
     * 根据菜品id批量删除菜品口味信息
     * @param dishIds
     */
    void deleteFlavorByIds(List<Long> dishIds);
}
