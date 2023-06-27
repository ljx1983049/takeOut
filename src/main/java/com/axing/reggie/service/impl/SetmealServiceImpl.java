package com.axing.reggie.service.impl;

import com.axing.reggie.domain.Setmeal;
import com.axing.reggie.domain.SetmealDish;
import com.axing.reggie.dto.DishDto;
import com.axing.reggie.dto.PageDto;
import com.axing.reggie.dto.SetmealDto;
import com.axing.reggie.exception.BusinessException;
import com.axing.reggie.mapper.SetmealMapper;
import com.axing.reggie.service.CategoryService;
import com.axing.reggie.service.DishService;
import com.axing.reggie.service.SetmealDishService;
import com.axing.reggie.service.SetmealService;
import com.axing.reggie.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;

    /**
     * 根据分类id查询关联套餐的数量
     * @param id
     * @return
     */
    @Override
    public int countByCategoryId(Long id) {
        return setmealMapper.countByCategoryId(id);
    }

    /**
     * 新增套餐
     * @param setmealDto
     */
    @Override
    @Transactional
    public void save(SetmealDto setmealDto) {
        //新增套餐
        setmealMapper.save(setmealDto);

        //获取套餐菜品
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //设置每个菜品管理的套餐id
        if (setmealDishes.size()>0) {
            for (SetmealDish sd : setmealDishes) {
                sd.setSetmealId(setmealDto.getId());
            }
            //新增套餐菜品关系
            setmealDishService.save(setmealDishes);
        }
    }

    /**
     * 获取套餐界面信息
     * @param pageDto
     * @return
     */
    @Override
    public PageVo<SetmealDto> getPageList(PageDto pageDto) {
        //查询总记录条数
        int total = setmealMapper.getTotal(pageDto);
        //查询套餐页面信息
        List<SetmealDto> setmealDtoList = setmealMapper.getPageListByCondition(pageDto);
        //封装vo
        PageVo<SetmealDto> vo = new PageVo<>();
        vo.setTotal(total);
        vo.setRecords(setmealDtoList);

        //获取套餐分类名称
        for (SetmealDto sd:setmealDtoList) {
            Long categoryId = sd.getCategoryId();
            //根据套餐分类id查询套餐分类名称
            String name = categoryService.getNameById(categoryId);
            sd.setCategoryName(name);
        }
        return vo;
    }

    /**
     * 删除套餐和套餐菜品关系
     * @param ids
     */
    @Override
    @Transactional
    public void deleteSetmealAndSetmealDish(List<Long> ids) {
        //判断套餐状态，售卖状态中不能删除
        int count = setmealMapper.getCountByStatus(ids);

        if (count>0){
            //删除的套餐中有起售状态
            throw new BusinessException("套餐在起售状态，不能删除");
        }

        //根据id删除套餐,可多条删除
        setmealMapper.deleteByIds(ids);

        //删除套餐菜品关系表
        setmealDishService.deleteBySetmealIds(ids);

    }

    /**
     * 查询关联的套餐名称
     * @param setmealIds
     * @return
     */
    @Override
    public List<String> getNameBySetmealIds(List<Long> setmealIds) {
        return setmealMapper.getSetmealNameById(setmealIds);
    }

    /**
     * 修改套餐售卖状态
     * @param status
     * @param ids
     */
    @Override
    public void updateStatus(int status, List<Long> ids) {
        setmealMapper.updateSetmealStatusByIds(status,ids);
    }

    /**
     * 根据id获取套餐信息
     * @param id
     * @return
     */
    @Override
    public SetmealDto getSetmealById(Long id) {
        Setmeal setmeal = setmealMapper.getSetmealById(id);
        SetmealDto setmealDto = new SetmealDto();
        //对象拷贝
        BeanUtils.copyProperties(setmeal,setmealDto);
        //查询套餐关联的菜品
        List<SetmealDish> setmealDishList =setmealDishService.getSetmealDish(id);
        setmealDto.setSetmealDishes(setmealDishList);
        return setmealDto;
    }

    /**
     * 修改套餐信息
     * @param setmealDto
     */
    @Override
    @Transactional
    public void updateSetmeal(SetmealDto setmealDto) {
        //修改套餐
        setmealMapper.updateSetmealById(setmealDto);

        //删除原有的套餐菜品关系表的关联数据
        Long id = setmealDto.getId();
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        setmealDishService.deleteBySetmealIds(ids);

        //获取套餐菜品
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //设置每个菜品管理的套餐id
        if (setmealDishes.size()>0) {
            for (SetmealDish sd : setmealDishes) {
                sd.setSetmealId(setmealDto.getId());
            }
            //新增套餐菜品关系
            setmealDishService.save(setmealDishes);
        }
    }

    /**
     * 查询套餐信息列表
     * @return
     */
    @Override
    public List<SetmealDto> getSetmealList(Setmeal setmeal) {
        return setmealMapper.getSetmealListByCategoryId(setmeal);
    }

    /**
     * 根据套餐id查询套餐关联的菜品详细信息
     * @param setmealId
     * @return
     */
    @Override
    public List<DishDto> getDish(Long setmealId) {
        //根据套餐id 查询 套餐菜品表的 菜品id
        List<Long> dishIds = setmealDishService.getDishIdBySetmealId(setmealId);
        //根据菜品id查询菜品表的信息
        List<DishDto> dishDtoList = dishService.getDishListByIds(dishIds);

        //根据菜品id和套餐id 查询 套餐菜品表 的份量数
        for (DishDto dd:dishDtoList) {
            dd.setCopies(setmealDishService.getCopies(dd.getId(),setmealId));
        }
        return dishDtoList;
    }
}
