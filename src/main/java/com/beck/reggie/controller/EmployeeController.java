package com.beck.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beck.reggie.common.R;
import com.beck.reggie.entity.Employee;
import com.beck.reggie.service.EmployeeService;
import lombok.Getter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login( @RequestBody Employee employee, HttpServletRequest request) {
        String pwd = employee.getPassword();
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername())
                .eq(Employee::getPassword, pwd);
        Employee emp = employeeService.getOne(queryWrapper);
        if (emp == null) {
            return R.error("登录失败,账号或密码错误");
        }
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
//        清理Session中保存的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee, HttpServletRequest request) {
        log.info("新增员工信息{}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
////        从session中获取当前登录用户的id
////        Long employee1 = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(111111111111L);
//        employee.setUpdateUser(111111111111L);
        boolean save = employeeService.save(employee);
        if (save) {
            return R.success("增加成功");
        }
        return R.error("增加失败");
    }

    @GetMapping("/page")
    public R<Page> page(
            int page,
            int pageSize,
            String name
    ) {
        log.info("page={},pageSize={},name={}",page,pageSize,name);

//        分页构造器
        Page page1 = new Page(page,pageSize);
//        条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(name != null && name != "",Employee::getName,name)
                .orderByDesc(Employee::getUpdateTime);
//        查询
        employeeService.page(page1,queryWrapper);

        return R.success(page1);
    }

    @PutMapping
    public R<String> update(@RequestBody Employee employee){
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(111111112222L);
        boolean b = employeeService.updateById(employee);
        if (b){
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }
    @GetMapping("/GetById")
    public R<Employee> getById(Long id){
        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("查询为空");
    }
}
