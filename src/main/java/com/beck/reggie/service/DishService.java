package com.beck.reggie.service;

import com.beck.reggie.Dto.DishDto;
import com.beck.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 13768
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-07-20 16:19:18
*/
public interface DishService extends IService<Dish> {
    //新增菜品,同时插入菜品对应的口味,需要操作两张表,dish,dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

//    修改菜品信息
    public void updateWithFlavor(DishDto dishDto);
}
