package com.beck.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beck.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper  extends BaseMapper<Employee> {

}
