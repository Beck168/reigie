package com.beck.reggie.service;

import com.beck.reggie.Dto.SetmealDto;
import com.beck.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 13768
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-07-20 16:21:03
*/
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐,同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
