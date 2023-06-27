package com.axing.reggie.service;

import com.axing.reggie.domain.Dish;
import com.axing.reggie.dto.DishDto;
import com.axing.reggie.dto.PageDto;
import com.axing.reggie.vo.PageVo;

import java.util.List;

public interface DishService {
    int countByCategoryId(Long id);

    int save(DishDto dishDto);

    PageVo<DishDto> pageList(PageDto pageDto);

    DishDto getDishById(Long id);

    void updateDish(DishDto dishDto);

    // List<Dish> getDishByCondition(Dish dish);
    List<DishDto> getDishByCondition(Dish dish);

    void updateDishStatus(int status, List<Long> ids);

    void deleteDish(List<Long> ids);

    List<DishDto> getDishListByIds(List<Long> ids);

    DishDto getDishDtoById(Long id);
}
