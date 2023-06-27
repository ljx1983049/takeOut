package com.axing.reggie.service.impl;

import com.axing.reggie.domain.Category;
import com.axing.reggie.exception.BusinessException;
import com.axing.reggie.mapper.CategoryMapper;
import com.axing.reggie.service.CategoryService;
import com.axing.reggie.service.DishService;
import com.axing.reggie.service.SetmealService;
import com.axing.reggie.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
// public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishService dishService;//菜品管理业务层
    @Autowired
    private SetmealService setmealService;//套餐管理业务层

    /**
     * 新增分类
     * @param category
     * @return
     */
    @Override
    public int save(Category category) {
        System.out.println("-------------------调用了service层的方法---------------------");
        int count = categoryMapper.save(category);
        return count;
    }

    @Override
    public PageVo<Category> pageList(Map<String, Integer> map) {
        //查询总记录条数
        int total = categoryMapper.getTotal();
        //查询分类信息页面列表
        List<Category> categoryList = categoryMapper.getPageList(map);
        PageVo<Category> vo = new PageVo<>();
        vo.setTotal(total);
        vo.setRecords(categoryList);
        return vo;
    }

    @Override
    public int deleteById(Long id) {
        //查询当前分类是否关联了菜品分类，如果已经关联，抛出一个异常
        int count1 = dishService.countByCategoryId(id);
        if (count1>0){
            throw new BusinessException("当前分类下已关联了菜品，不能删除");
        }
        //查询当前分类是否关联了套餐分类，如果已经关联，抛出一个异常
        int count2 = setmealService.countByCategoryId(id);
        if (count2>0){
            throw new BusinessException("当前分类下已关联了套餐，不能删除");
        }
        //正常删除分类
        return categoryMapper.deleteById(id);
    }

    @Override
    public int update(Category category) {
         return categoryMapper.updateById(category);
    }

    @Override
    public List<Category> list(Category category) {
        return categoryMapper.getListByType(category);
    }

    /**
     * 根据id查询菜品名称
     * @param categoryId
     * @return
     */
    @Override
    public String getNameById(Long categoryId) {
        return categoryMapper.getNameById(categoryId);
    }


}
