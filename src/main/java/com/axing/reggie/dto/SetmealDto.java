package com.axing.reggie.dto;


import com.axing.reggie.domain.Setmeal;
import com.axing.reggie.domain.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;//套餐菜品关系 信息列表

    private String categoryName;//套餐分类名称
}
