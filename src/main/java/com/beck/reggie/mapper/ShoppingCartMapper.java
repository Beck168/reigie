package com.beck.reggie.mapper;

import com.beck.reggie.entity.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 13768
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2022-07-27 09:50:42
* @Entity com.beck.reggie.entity.ShoppingCart
*/
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




