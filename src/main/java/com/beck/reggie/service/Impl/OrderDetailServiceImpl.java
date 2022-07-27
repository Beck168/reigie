package com.beck.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beck.reggie.entity.OrderDetail;
import com.beck.reggie.service.OrderDetailService;
import com.beck.reggie.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author 13768
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-07-27 11:12:49
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




