package com.axing.reggie.service.impl;

import com.axing.reggie.domain.Dish;
import com.axing.reggie.domain.DishFlavor;
import com.axing.reggie.dto.DishDto;
import com.axing.reggie.dto.PageDto;
import com.axing.reggie.exception.BusinessException;
import com.axing.reggie.mapper.DishMapper;
import com.axing.reggie.service.*;
import com.axing.reggie.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    // private StringRedisTemplate stringRedisTemplate;
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询关联菜品的数量
     * @param id
     * @return
     */
    @Override
    public int countByCategoryId(Long id) {
        return dishMapper.countByCategoryId(id);
    }

    /**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     * @return
     */
    @Override
    @Transactional
    public int save(DishDto dishDto) {

        //保存菜品的基本信息到菜品表
        int count = dishMapper.save(dishDto);

        Long dishId = dishDto.getId();//获取菜品id,添加完菜品信息，自动生成

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        if (flavors.size() > 0) {
            for (DishFlavor dishFlavor : flavors) {
                dishFlavor.setDishId(dishId);
            }
            log.info("遍历菜品口味赋值后：{}", flavors);

            //保存菜品口味数据到菜品口味表dish_flavor
            int count2 = dishFlavorService.save(flavors);
            log.info("保存了{}条数据", count2);
        }

        //清除redis缓存,根据分类精确清除
        String key = "dish_"+dishDto.getCategoryId()+"_1";
        redisTemplate.delete(key);

        return count;
    }

    /**
     * 查询界面信息
     * @param pageDto
     * @return
     */
    @Override
    public PageVo<DishDto> pageList(PageDto pageDto) {
        //查询总记录条数
        int total = dishMapper.getTotalByCondition(pageDto.getName());
        //查询页面显示列表信息
        // List<Dish> dishList = dishMapper.getDishListByCondition(pageDto);
        List<DishDto> dishDtoList = dishMapper.getDishListByCondition(pageDto);
        for (DishDto dish:dishDtoList) {
            //取出菜品分类id
            Long categoryId = dish.getCategoryId();
            String categoryName = categoryService.getNameById(categoryId);
            dish.setCategoryName(categoryName);
        }
        log.info("列表信息：{}",dishDtoList);

        //封装vo
        PageVo<DishDto> vo = new PageVo<>();
        vo.setTotal(total);
        vo.setRecords(dishDtoList);
        return vo;
    }

    /**
     * 根据id获取菜品信息
     * @param id
     * @return
     */
    @Override
    public DishDto getDishById(Long id) {
        //查询菜品信息
        Dish dish = dishMapper.getDishById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //根据菜品id获取菜品口味信息列表
        List<DishFlavor> dishFlavorList = dishFlavorService.getFlavorListByDishId(dish.getId());
        //设置菜品口味
        dishDto.setFlavors(dishFlavorList);
        return dishDto;
    }

    /**
     * 修改菜品信息
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateDish(DishDto dishDto) {
        //修改菜品信息
        dishMapper.updateDishById(dishDto);
        //获取菜品id
        Long dishId = dishDto.getId();

        //删除原始的口味信息
        dishFlavorService.deleteByDishId(dishId);
        //获取菜品口味信息列表
        List<DishFlavor> flavors = dishDto.getFlavors();
        //添加口味信息
        if (flavors.size() != 0) {
            for (DishFlavor dishFlavor : flavors) {
                dishFlavor.setDishId(dishId);
            }
            //保存菜品口味数据到菜品口味表dish_flavor
            dishFlavorService.save(flavors);
        }

        //清除redis缓存,根据分类精确清除
        String key = "dish_"+dishDto.getCategoryId()+"_1";
        redisTemplate.delete(key);
    }

    /**
     * 根据条件查询菜品信息
     * @param dish
     * @return
     */
    // @Override
    // public List<Dish> getDishByCondition(Dish dish) {
    //     return dishMapper.getDishByCondition(dish);
    // }
    @Override
    public List<DishDto> getDishByCondition(Dish dish) {
        List<DishDto> dishDtoList = null;
        //从redis中取数据
        String key = "dish_"+dish.getCategoryId()+"_"+dish.getStatus();//动态构造key，dish_1397844303408574465_1
        // dishDtoList = stringRedisTemplate.opsForValue().get(key);
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
        if (dishDtoList == null){
            //redis中没有数据
            //查询数据库
            dishDtoList = dishMapper.getDishByCondition(dish);
            for (DishDto dd:dishDtoList) {
                //根据菜品id查询菜品口味
                List<DishFlavor> flavorListByDishId = dishFlavorService.getFlavorListByDishId(dd.getId());
                dd.setFlavors(flavorListByDishId);
            }
            // 将数据存入redis中
            redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.HOURS);
        }
        //redis中有数据,直接返回
        return dishDtoList;
    }

    /**
     * 修改菜品售卖状态
     * @param status
     * @param ids
     */
    @Override
    public void updateDishStatus(int status, List<Long> ids) {
        dishMapper.updateStatusByIds(status,ids);
    }

    /**
     * 删除菜品信息
     * @param ids
     */
    @Override
    @Transactional
    public void deleteDish(List<Long> ids) {
        //判断该菜品有没被套餐关联
        //查询被什么套餐关联
        List<Long> setmealIds = setmealDishService.getSetmealIdByDishId(ids);
        if (setmealIds.size()>0){
            //有被套餐关联
            //查询关联的套餐名称
            List<String> names = setmealService.getNameBySetmealIds(setmealIds);
            throw new BusinessException("当前菜品在套餐"+names+"中关联，请先修改或删除套餐");
        }
        //删除菜品
        dishMapper.deleteDishByids(ids);
        //删除口味
        dishFlavorService.deleteByDishIds(ids);

        //清除所有菜品redis缓存
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
    }

    /**
     * 根据ids查询菜品详细信息列表
     * @param ids
     * @return
     */
    @Override
    public List<DishDto> getDishListByIds(List<Long> ids) {
        return dishMapper.getDishListByIds(ids);
    }

    /**
     * 根据菜品id查询菜品表的信息分装到dishDto排除id查询
     * @param id
     * @return
     */
    @Override
    public DishDto getDishDtoById(Long id) {
        return dishMapper.getDishDtoById(id);
    }
}
