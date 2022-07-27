package com.beck.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.beck.reggie.common.R;
import com.beck.reggie.entity.User;
import com.beck.reggie.service.UserService;
import com.beck.reggie.utils.SMSUtils;
import com.beck.reggie.utils.ValidateCodeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 发送手机验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            //生成4位数验证码
            String s = ValidateCodeUtils.generateValidateCode(4).toString();

            //调用阿里云短信服务api
            SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,s);

            //需要将生成的验证码保存到session
            session.setAttribute(phone,s);
            return R.success("发送成功");
        }

        return R.error("发送失败");
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map user, HttpSession session){
        //获取手机号
        String phone = user.get("phone").toString();
//        获取验证码
        String code = user.get("code").toString();

        //从session中获取保存的验证码
        Object attribute = session.getAttribute(phone);

        if(attribute == null && attribute.equals(code)){
            LambdaQueryWrapper<User> queryWrapper  = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user1 = userService.getOne(queryWrapper);
            if (user1 == null) {
                //判断当前用户是否是新用户
                 user1 = new User();
                user1.setPhone(phone);
                user1.setStatus(1);
                userService.save(user1);

            }
            R.success(user1);
        }


        return R.error("登录失败");
    }
}
