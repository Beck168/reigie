package com.beck.reggie.mapper;

import com.beck.reggie.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 13768
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2022-07-25 15:32:29
* @Entity com.beck.reggie.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




