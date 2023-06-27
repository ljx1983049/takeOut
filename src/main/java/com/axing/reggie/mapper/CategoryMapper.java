package com.axing.reggie.mapper;

import com.axing.reggie.domain.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    int save(Category category);

    //查询总记录条数
    int getTotal();

    //查询页面分类信息列表
    List<Category> getPageList(Map<String, Integer> map);

    //根据id删除分类信息
    int deleteById(Long id);

    //根据id修改分类信息
    int updateById(Category category);

    //根据类型 type=分类类型 查询分类信息，按照sort排序，sort一样就按照修改时间排序
    List<Category> getListByType(Category category);

    //根据id查询菜品名称
    String getNameById(Long categoryId);
}
