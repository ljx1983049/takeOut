package com.axing.reggie.service.impl;

import com.axing.reggie.domain.DishFlavor;
import com.axing.reggie.mapper.DishFlavorMapper;
import com.axing.reggie.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishFlavorServiceImpl implements DishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 添加菜品口味，支持多条添加
     * @param flavors
     * @return
     */
    @Override
    public int save(List<DishFlavor> flavors) {
        return dishFlavorMapper.save(flavors);
    }

    @Override
    public List<DishFlavor> getFlavorListByDishId(Long dishId) {
        List<DishFlavor> dishFlavorList = dishFlavorMapper.getFlavorListByDishId(dishId);
        return dishFlavorList;
    }

    @Override
    public void deleteByDishId(Long dishId) {
        dishFlavorMapper.deleteFlavorById(dishId);
    }

    @Override
    public void deleteByDishIds(List<Long> ids) {
        dishFlavorMapper.deleteFlavorByIds(ids);
    }
}
