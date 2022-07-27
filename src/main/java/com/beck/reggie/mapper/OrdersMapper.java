package com.beck.reggie.mapper;

import com.beck.reggie.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 13768
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2022-07-27 11:12:45
* @Entity com.beck.reggie.entity.Orders
*/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




