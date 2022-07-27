package com.beck.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beck.reggie.Dto.SetmealDto;
import com.beck.reggie.entity.SetmealDish;
import com.beck.reggie.service.SetmealDishService;
import com.beck.reggie.mapper.SetmealDishMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author 13768
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Service实现
* @createDate 2022-07-23 15:09:51
*/
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish>
    implements SetmealDishService{


}




