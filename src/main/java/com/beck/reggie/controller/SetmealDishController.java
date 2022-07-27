package com.beck.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beck.reggie.Dto.SetmealDto;
import com.beck.reggie.common.R;
import com.beck.reggie.domain.Category;
import com.beck.reggie.entity.Setmeal;
import com.beck.reggie.service.CategoryService;
import com.beck.reggie.service.SetmealDishService;
import com.beck.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/Setmeal")
@Slf4j
public class SetmealDishController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("新增成功");
    }

    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
//        分页构造器
        Page<Setmeal> pageInfo = new Page(page,pageSize);
        Page<SetmealDto> pageDto = new Page();

//        条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<Setmeal>();
        queryWrapper.like(name != null, Setmeal::getName,name)
                .orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);

//        对象拷贝
        BeanUtils.copyProperties(pageInfo,pageDto,"records");
        List<Setmeal> records = pageInfo.getRecords();


        List<SetmealDto> list = records.stream().map((item) ->{
            SetmealDto dto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item,dto);
//            分类id
            Long categoryId = item.getCategoryId();
//            获取分类id
            Category byId = categoryService.getById(categoryId);
            if (byId != null) {
                String name1 = byId.getName();
                dto.setCategoryName(name1);
            }
            return dto;
        }).collect(Collectors.toList());
        pageDto.setRecords(list);
        return R.success(pageDto) ;

    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }

    /**
     * 根据条件查询套餐数据
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId,setmeal.getCategoryId())
                .eq(setmeal.getStatus() != null, Setmeal::getStatus,setmeal.getStatus())
                .orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);
    }
}
