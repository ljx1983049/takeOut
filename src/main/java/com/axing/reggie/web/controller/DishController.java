package com.axing.reggie.web.controller;

import com.axing.reggie.common.R;
import com.axing.reggie.domain.Dish;
import com.axing.reggie.dto.DishDto;
import com.axing.reggie.dto.PageDto;
import com.axing.reggie.service.DishService;
import com.axing.reggie.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 添加菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        int count = dishService.save(dishDto);
        if (count!=1){
            return R.error("新增菜品失败");
        }
        return R.success("新增菜品成功");
    }

    /**
     * 查询菜品管理页面信息
     * @param pageDto
     * @return
     */
    @GetMapping("page")
    public R<PageVo<DishDto>> page(PageDto pageDto){
        log.info("接收到的参数{}",pageDto.toString());
        PageVo<DishDto> vo = dishService.pageList(pageDto);
        return R.success(vo);
    }

    /**
     * 根据id查询菜品信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getDishById(@PathVariable Long id){
        DishDto dishDto = dishService.getDishById(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品信息
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto){
        dishService.updateDish(dishDto);
        return R.success("修改菜品信息成功");
    }

    /**
     * 根据条件查询菜品信息
     * @param dish
     * @return
     */
    // @GetMapping("/list")
    // public R<List<Dish>> list(Dish dish){
    //     List<Dish> dishList = dishService.getDishByCondition(dish);
    //     return R.success(dishList);
    // }
    @GetMapping("/list") //返回多一个口味信息
    public R<List<DishDto>> list(Dish dish){
        List<DishDto> dishDtoList = dishService.getDishByCondition(dish);
        return R.success(dishDtoList);
    }

    /**
     * 修改菜品售卖状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable int status,@RequestParam List<Long> ids){
        dishService.updateDishStatus(status,ids);
        return R.success("菜品售卖状态修改成功");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        dishService.deleteDish(ids);
        return R.success("删除菜品成功");
    }

}
