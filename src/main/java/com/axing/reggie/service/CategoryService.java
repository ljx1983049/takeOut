package com.axing.reggie.service;

import com.axing.reggie.domain.Category;
import com.axing.reggie.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface CategoryService{// extends IService<Category> {

    int save(Category category);

    PageVo<Category> pageList(Map<String, Integer> map);

    int deleteById(Long ids);

    int update(Category category);

    List<Category> list(Category category);

    String getNameById(Long categoryId);
}
