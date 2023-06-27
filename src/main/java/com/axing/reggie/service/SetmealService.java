package com.axing.reggie.service;

import com.axing.reggie.domain.Dish;
import com.axing.reggie.domain.Setmeal;
import com.axing.reggie.domain.SetmealDish;
import com.axing.reggie.dto.DishDto;
import com.axing.reggie.dto.PageDto;
import com.axing.reggie.dto.SetmealDto;
import com.axing.reggie.vo.PageVo;

import java.util.List;

public interface SetmealService {
    int countByCategoryId(Long id);

    void save(SetmealDto setmealDto);

    PageVo<SetmealDto> getPageList(PageDto pageDto);

    void deleteSetmealAndSetmealDish(List<Long> ids);


    List<String> getNameBySetmealIds(List<Long> setmealIds);

    void updateStatus(int status, List<Long> ids);

    SetmealDto getSetmealById(Long id);

    void updateSetmeal(SetmealDto setmealDto);

    List<SetmealDto> getSetmealList(Setmeal setmeal);

    List<DishDto> getDish(Long setmealId);
}
