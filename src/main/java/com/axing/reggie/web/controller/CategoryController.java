package com.axing.reggie.web.controller;

import com.axing.reggie.common.R;
import com.axing.reggie.domain.Category;
import com.axing.reggie.service.CategoryService;
import com.axing.reggie.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("接受到对象category:{}",category);
        int count = categoryService.save(category);
        if(count!=1){
            return R.error("新增分类失败");
        }
        return R.success("新增分类成功");
    }

    /**
     * 显示页面分类列表信息
     * @param page 页码
     * @param pageSize  显示条数
     * @return
     */
    @GetMapping("/page")
    public R<PageVo> pageList(int page,int pageSize){
        //计算从哪条数据开始查询
        int startNum = (page-1)*pageSize;
        Map<String,Integer> map = new HashMap<>();
        map.put("startNum",startNum);
        map.put("pageSize",pageSize);
        PageVo<Category> vo = categoryService.pageList(map);
        return R.success(vo);
    }

    /**
     * 根据id删除分类信息
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteById(Long ids){
        int count = categoryService.deleteById(ids);
        if (count!=1){
            return R.error("删除分类信息失败");
        }
        return R.success("删除分类信息成功");
    }

    /**
     * 修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        int count = categoryService.update(category);
        if (count!=1){
            return R.error("修改失败");
        }
        return R.success("修改分类信息成功");
    }

    /**
     * 根据类型 type=分类类型 查询分类信息，按照sort排序，sort一样就按照修改时间排序
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        List<Category> categoryList = categoryService.list(category);
        return R.success(categoryList);
    }











}
