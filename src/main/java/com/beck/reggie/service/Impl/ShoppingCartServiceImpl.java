package com.beck.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beck.reggie.entity.ShoppingCart;
import com.beck.reggie.service.ShoppingCartService;
import com.beck.reggie.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author 13768
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2022-07-27 09:50:42
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




