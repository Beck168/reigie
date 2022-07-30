package com.beck.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beck.reggie.Dto.DishDto;
import com.beck.reggie.common.R;
import com.beck.reggie.domain.Category;
import com.beck.reggie.entity.Dish;
import com.beck.reggie.entity.DishFlavor;
import com.beck.reggie.service.CategoryService;
import com.beck.reggie.service.DishFlavorService;
import com.beck.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    private RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("添加菜品成功");
    }

    /**
     * 菜品信息分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
//        分页构造器
        Page<Dish> pageINfo = new Page(page,pageSize);
        Page<DishDto> dtoPage = new Page<>();
//        条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!= null,Dish::getName,name)
                    .orderByDesc(Dish::getUpdateTime);

        dishService.page(pageINfo,queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageINfo,dtoPage,"records");
        List<Dish> records = pageINfo.getRecords();
        List<DishDto> list = records.stream().map((item) ->{
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            Category category = categoryService.getById(categoryId);
           if (category != null) {
               String categoryName = category.getName();
               dishDto.setCategoryName(categoryName);
           }
            return dishDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto byIdWithFlavor = dishService.getByIdWithFlavor(id);
        return R.success(byIdWithFlavor);
    }

    /**
     * 修改菜品信息
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(DishDto dishDto) {
         dishService.updateWithFlavor(dishDto);
//         清理所有菜品数据
//        Set keys = redisTemplate.keys("dish_*");

//        精确清理某个菜品缓存
        String keys = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(keys);
        return R.success("修改成功");
    }

    /**
     *
     * @param dish
     * @return
     */
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//        //构造条件查询
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId())
//                .orderByAsc(Dish::getSort)
//                .orderByDesc(Dish::getUpdateTime)
//                //添加条件,查询状态为1(1为在售,2为停售)
//                .eq(Dish::getStatus,1);
//        List<Dish> list = dishService.list(queryWrapper);
//
//        return R.success(list);
//    }


    /**
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        List<DishDto>  dishDtos = null;

        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();
//        现从redis中获取菜品数据
        dishDtos = (List<DishDto> )redisTemplate.opsForValue().get(key);
        if (dishDtos != null){
            //        如果存在,直接返回无需查询数据库
            R.success(dishDtos);
        }



        //构造条件查询
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId())
                .orderByAsc(Dish::getSort)
                .orderByDesc(Dish::getUpdateTime)
                //添加条件,查询状态为1(1为在售,2为停售)
                .eq(Dish::getStatus,1);
        List<Dish> list = dishService.list(queryWrapper);
        dishDtos = list.stream().map((item) ->{
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            Long id = item.getId();//当前菜品id
            LambdaQueryWrapper<DishFlavor> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(DishFlavor::getId,id);
            List<DishFlavor> list1 = dishFlavorService.list(queryWrapper1);
            dishDto.setFlavors(list1);
            return dishDto;
        }).collect(Collectors.toList());
//        如果不存在,查询数据库后缓存到redis中并返回
        redisTemplate.opsForValue().set(key,dishDtos,60, TimeUnit.MINUTES);
        return R.success(dishDtos);
    }
}
