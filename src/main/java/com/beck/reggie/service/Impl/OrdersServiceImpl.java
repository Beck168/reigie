package com.beck.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beck.reggie.entity.Orders;
import com.beck.reggie.service.OrdersService;
import com.beck.reggie.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author 13768
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2022-07-27 11:12:45
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}




