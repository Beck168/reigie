package com.beck.reggie.service;

import com.beck.reggie.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 13768
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-07-20 15:10:51
*/
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
