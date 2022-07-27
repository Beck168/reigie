package com.beck.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.beck.reggie.common.BaseContext;
import com.beck.reggie.common.R;
import com.beck.reggie.entity.ShoppingCart;
import com.beck.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ShoppingCar")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart cart) {
        log.info("购物车数据:{}",cart);
//        设置用户id,指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        cart.setUserId(currentId);

//        查询当前菜品或者套餐是否在购物车中
        Long dishId = cart.getDishId();
        Long setmealId = cart.getSetmealId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (dishId != null) {
//            添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        }else if (setmealId != null){
//            添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
        }
        //        查询当前菜品或者套餐是否在购物车中
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);
        if (one != null) {
//            如果存在,就在原来的基础上数量加一
            Integer number = one.getNumber();
            one.setNumber(number + 1);
            shoppingCartService.updateById(one);
        }else {
            cart.setNumber(1);
            shoppingCartService.save(cart);
            one = cart;
        }
        return R.success(one);
    }


    /**
     * 查询购物车数据
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> getList(Long userId){
        log.info("查看购物车");
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userId)
                .orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clear")
    public R<String> delete(Long userId){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userId);
        shoppingCartService.removeById(userId);
        return R.success("清空购物车成功");
    }
}
