package com.beck.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beck.reggie.Dto.SetmealDto;
import com.beck.reggie.common.CustomException;
import com.beck.reggie.entity.Setmeal;
import com.beck.reggie.entity.SetmealDish;
import com.beck.reggie.service.SetmealDishService;
import com.beck.reggie.service.SetmealService;
import com.beck.reggie.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 13768
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2022-07-20 16:21:03
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐,同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
//        保存套餐的基本信息,操作Setmeal
        this.save(setmealDto);
//        保存套餐和菜品的关联信息,
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item) ->{
            item.setSetmealId(setmealDto.getId().toString());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);

    }

    /**
     * 删除套餐
     * @param ids
     */
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态
        LambdaQueryWrapper<Setmeal>  queryWrapper = new LambdaQueryWrapper<Setmeal>();

        queryWrapper.in(Setmeal::getId,ids)
                .eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);


        if (count > 0) {
            //
            throw new CustomException("套餐正在售卖中,不能删除");
        }
//        如果可以删除,先删除套餐表中的数据
        this.removeByIds(ids);
        //再删除关系表中的数据
        LambdaQueryWrapper<SetmealDish>  queryWrapperDish = new LambdaQueryWrapper<>();
        queryWrapperDish.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapperDish);

    }
}




