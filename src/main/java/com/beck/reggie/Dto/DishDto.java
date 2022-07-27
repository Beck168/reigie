package com.beck.reggie.Dto;

import com.beck.reggie.entity.Dish;
import com.beck.reggie.entity.DishFlavor;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
