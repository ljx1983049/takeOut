package com.axing.reggie.mapper;

import com.axing.reggie.domain.Dish;
import com.axing.reggie.dto.DishDto;
import com.axing.reggie.dto.PageDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    //根据分类id查询关联菜品的数量
    int countByCategoryId(Long id);

    /**
     * 保存菜品信息
     * @param dishDto
     * @return
     */
    int save(DishDto dishDto);

    /**
     * 根据条件查询菜品总记录条数
     * @param name
     * @return
     */
    int getTotalByCondition(String name);

    /**
     * 根据条件查询菜品分页信息
     * @param pageDto
     * @return
     */
    List<DishDto> getDishListByCondition(PageDto pageDto);

    /**
     * 根据id查询菜品信息
     * @param id
     * @return
     */
    Dish getDishById(Long id);

    /**
     * 根据菜品id修改菜品信息
     * @param dishDto
     */
    void updateDishById(DishDto dishDto);

    /**
     * 根据条件查询菜品信息
     * @param dish
     * @return
     */
    // List<Dish> getDishByCondition(Dish dish);
     List<DishDto> getDishByCondition(Dish dish);

    /**
     * 根据ids批量修改菜品状态
     * @param status
     * @param ids
     */
    void updateStatusByIds(@Param("status") int status, @Param("list") List<Long> ids);

    /**
     * 根据id删除菜品信息
     * @param ids
     */
    void deleteDishByids(List<Long> ids);

    /**
     * 根据ids查询菜品信息列表
     * @param ids
     * @return
     */
    List<DishDto> getDishListByIds(List<Long> ids);

    /**
     * 根据菜品id查询菜品表的信息分装到dishDto排除id查询
     * @param id
     * @return
     */
    DishDto getDishDtoById(Long id);
}
