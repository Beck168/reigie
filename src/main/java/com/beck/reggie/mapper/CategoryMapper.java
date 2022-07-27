package com.beck.reggie.mapper;

import com.beck.reggie.domain.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 13768
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-07-20 15:10:51
* @Entity com.beck.reggie.domain.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




