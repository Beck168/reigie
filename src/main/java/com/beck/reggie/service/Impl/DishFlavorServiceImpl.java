package com.beck.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beck.reggie.entity.DishFlavor;
import com.beck.reggie.service.DishFlavorService;
import com.beck.reggie.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author 13768
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-07-21 15:33:28
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




