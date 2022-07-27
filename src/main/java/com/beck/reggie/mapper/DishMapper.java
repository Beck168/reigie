package com.beck.reggie.mapper;

import com.beck.reggie.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 13768
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-07-20 16:19:18
* @Entity com.beck.reggie.entity.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}




