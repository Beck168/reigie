package com.beck.reggie.mapper;

import com.beck.reggie.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 13768
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2022-07-27 11:12:49
* @Entity com.beck.reggie.entity.OrderDetail
*/

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




