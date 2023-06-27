package com.axing.reggie.mapper;

import com.axing.reggie.domain.SetmealDish;
import com.axing.reggie.dto.DishDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

    /**
     * 添加套餐菜品关系，支持多条添加
     * @param setmealDishes
     */
    void save(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id批量删除套餐商品关系信息
     * @param ids
     */
    void deleteBySetmealIds(List<Long> ids);

    /**
     * 根据菜品id查询被关联套餐的id
     * @param ids
     * @return
     */
    List<Long> getSetmealIdByDishId(List<Long> ids);

    /**
     * 根据套餐id 查询套餐和菜品关联信息
     * @param setmealId
     * @return
     */
    List<SetmealDish> getSetmealDishBySetmealId(Long setmealId);

    /**
     * 根据套餐id 查询 套餐菜品表的 菜品id
     * @param setmealId
     * @return
     */
    List<Long> getDishIdBySetmealId(Long setmealId);

    /**
     * 根据菜品id和套餐id 查询 套餐菜品表 的份量数
     * @param dishId
     * @param setmealId
     * @return
     */
    Integer getCopies(@Param("dishId") Long dishId, @Param("setmealId") Long setmealId);
}
