package com.beck.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beck.reggie.common.CustomException;
import com.beck.reggie.domain.Category;
import com.beck.reggie.entity.Dish;
import com.beck.reggie.entity.Setmeal;
import com.beck.reggie.service.CategoryService;
import com.beck.reggie.mapper.CategoryMapper;
import com.beck.reggie.service.DishService;
import com.beck.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 13768
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-07-20 15:10:51
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除菜品分类(能删除的条件是菜品分类没有关联其他的菜品)
     * @param id
     */
    @Override
    public void remove(Long id) {
        //查询当前分类是否关联了菜品
        LambdaQueryWrapper<Dish> dishServiceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishServiceLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishServiceLambdaQueryWrapper);
        if (count > 0) {
            //关联了,抛出异常
            throw  new CustomException("当前分类下关联有菜品,无法删除");
        }
        //查询当前分类是否关联了套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count1 > 0){
            //关联了,抛出异常
            throw  new CustomException("当前分类下关联有套餐,无法删除");
        }
        //正常删除分类
        super.removeById(id);
    }
}




