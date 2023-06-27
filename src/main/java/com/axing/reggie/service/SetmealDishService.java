package com.axing.reggie.service;

import com.axing.reggie.domain.SetmealDish;
import com.axing.reggie.dto.DishDto;

import java.util.List;

public interface SetmealDishService {
    void save(List<SetmealDish> setmealDishes);

    void deleteBySetmealIds(List<Long> ids);

    List<Long> getSetmealIdByDishId(List<Long> ids);

    List<SetmealDish> getSetmealDish(Long id);

    List<Long> getDishIdBySetmealId(Long setmealId);

    Integer getCopies(Long id, Long setmealId);
}
