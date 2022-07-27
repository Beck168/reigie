package com.beck.reggie.Dto;

import com.beck.reggie.entity.Setmeal;
import com.beck.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
