package com.axing.reggie.mapper;

import com.axing.reggie.domain.Setmeal;
import com.axing.reggie.dto.PageDto;
import com.axing.reggie.dto.SetmealDto;
import com.axing.reggie.vo.PageVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    //根据分类id查询关联菜品的数量
    int countByCategoryId(Long id);

    /**
     * 新增套餐
     * @param setmealDto
     */
    void save(SetmealDto setmealDto);

    /**
     * 根据条件查询套餐页面信息列表
     * @param pageDto
     * @return
     */
    List<SetmealDto> getPageListByCondition(PageDto pageDto);

    /**
     * 根据条件查询总记录条数
     * @param pageDto
     * @return
     */
    int getTotal(PageDto pageDto);

    /**
     * 根据ids获取套餐状态为起售数量
     * @param ids
     * @return
     */
    int getCountByStatus(List<Long> ids);

    /**
     * 根据ids删除套餐
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根据id查询套餐名字
     * @param ids
     * @return
     */
    List<String> getSetmealNameById(List<Long> ids);

    /**
     * 根据id批量修改套餐售卖状态
     * @param status
     * @param ids
     */
    void updateSetmealStatusByIds(@Param("status") int status, @Param("list") List<Long> ids);

    /**
     * 根据id获取套餐信息
     * @param id
     * @return
     */
    Setmeal getSetmealById(Long id);

    /**
     * 根据套餐id修改套餐信息
     * @param setmealDto
     */
    void updateSetmealById(SetmealDto setmealDto);

    /**
     * 根据分类id查询套餐信息列表
     * @param setmeal
     * @return
     */
    List<SetmealDto> getSetmealListByCategoryId(Setmeal setmeal);
}
