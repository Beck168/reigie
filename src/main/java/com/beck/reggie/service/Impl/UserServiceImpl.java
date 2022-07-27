package com.beck.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beck.reggie.entity.User;
import com.beck.reggie.service.UserService;
import com.beck.reggie.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 13768
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-07-25 15:32:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




