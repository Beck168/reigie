package com.beck.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beck.reggie.Dto.DishDto;
import com.beck.reggie.common.R;
import com.beck.reggie.entity.Dish;
import com.beck.reggie.entity.DishFlavor;
import com.beck.reggie.service.DishFlavorService;
import com.beck.reggie.service.DishService;
import com.beck.reggie.mapper.DishMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 13768
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-07-20 16:19:18
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品,同时保存对应口味数据
     * @param dishDto
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);
        Long id = dishDto.getId();//菜品id
        //保存菜品口味数据
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
           item.setDishId(id);
           return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);

    }
    //根据id查询菜品和对应的口味信息
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
//        对象拷贝
        BeanUtils.copyProperties(dish,dishDto);
        //查询菜品对应的口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper =  new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(list);

        return dishDto;
    }

    @Transactional
    public void updateWithFlavor(DishDto dishDto){
//        更新菜品信息dish表
        this.updateById(dishDto);
        //删除当前菜品对应的口味
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);
//        更新菜品口味表
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
}




