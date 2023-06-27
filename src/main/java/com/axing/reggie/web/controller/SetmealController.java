package com.axing.reggie.web.controller;

import com.axing.reggie.common.R;
import com.axing.reggie.domain.Dish;
import com.axing.reggie.domain.Setmeal;
import com.axing.reggie.domain.SetmealDish;
import com.axing.reggie.dto.DishDto;
import com.axing.reggie.dto.PageDto;
import com.axing.reggie.dto.SetmealDto;
import com.axing.reggie.service.SetmealService;
import com.axing.reggie.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Api(tags = "套餐相关接口")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     * @param setmealDto
     */
    @PostMapping
    @CacheEvict(value = "setmealCache" ,allEntries = true)
    @ApiOperation(value = "新增套餐接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "setmealDto",value = "套餐信息数据",required = true)
    })
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.save(setmealDto);
        return R.success("新增套餐成功");
    }


    /**
     * 查询套餐界面信息
     * @param pageDto
     * @return
     */
    @GetMapping("/page")
    public R<PageVo<SetmealDto>> page(PageDto pageDto){
        PageVo<SetmealDto> vo = setmealService.getPageList(pageDto);
        return R.success(vo);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @CacheEvict(value = "setmealCache" ,allEntries = true)
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.deleteSetmealAndSetmealDish(ids);
        return R.success("删除套餐成功");
    }

    /**
     * 修改售卖状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable int status, @RequestParam List<Long> ids){
        setmealService.updateStatus(status,ids);
        return R.success("修改售卖状态成功");
    }

    /**
     * 根据id获取套餐信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getSetmeal(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getSetmealById(id);
        return R.success(setmealDto);
    }

    /**
     * 修改套餐
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateSetmeal(setmealDto);
        return R.success("修改套餐成功");
    }

    /**
     * 查询信息列表
     * @return
     */
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId+'_1'")
    @GetMapping("/list")
    public R<List<SetmealDto>> list(Setmeal setmeal){
        List<SetmealDto> setmealDtoList = setmealService.getSetmealList(setmeal);
        return R.success(setmealDtoList);
    }

    /**
     * 用户点击套餐查看菜品详情
     * @param setmealId
     * @return
     */
    @GetMapping("/dish/{setmealId}")
    public R<List<DishDto>> dish(@PathVariable Long setmealId){
        List<DishDto> dishDtoList = setmealService.getDish(setmealId);
        return R.success(dishDtoList);
    }













}
