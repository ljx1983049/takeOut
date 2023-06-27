package com.axing.reggie.service.impl;

import com.axing.reggie.domain.SetmealDish;
import com.axing.reggie.dto.DishDto;
import com.axing.reggie.mapper.SetmealDishMapper;
import com.axing.reggie.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealDishServiceImpl implements SetmealDishService {
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 添加套餐菜品关系，支持多条添加
     * @param setmealDishes
     */
    @Override
    public void save(List<SetmealDish> setmealDishes) {
        setmealDishMapper.save(setmealDishes);
    }

    /**
     * 根据套餐ids删除套餐商品关系
     * @param ids
     */
    @Override
    public void deleteBySetmealIds(List<Long> ids) {
        setmealDishMapper.deleteBySetmealIds(ids);
    }

    /**
     * 根据菜品id查询被关联套餐的id
     * @param ids
     * @return
     */
    @Override
    public List<Long> getSetmealIdByDishId(List<Long> ids) {
        return setmealDishMapper.getSetmealIdByDishId(ids);
    }

    /**
     * 查询套餐关联的菜品
     * @param id
     * @return
     */
    @Override
    public List<SetmealDish> getSetmealDish(Long id) {
        return setmealDishMapper.getSetmealDishBySetmealId(id);
    }

    /**
     * 根据套餐id 查询 套餐菜品表的 菜品id
     * @param setmealId
     * @return
     */
    @Override
    public List<Long> getDishIdBySetmealId(Long setmealId) {
        return setmealDishMapper.getDishIdBySetmealId(setmealId);
    }

    /**
     * 根据菜品id和套餐id 查询 套餐菜品表 的份量数
     * @param id
     * @param setmealId
     * @return
     */
    @Override
    public Integer getCopies(Long id, Long setmealId) {
        return setmealDishMapper.getCopies(id,setmealId);
    }


}
