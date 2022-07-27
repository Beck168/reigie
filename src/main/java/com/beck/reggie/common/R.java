package com.beck.reggie.common;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;
    private Map map;

    public static <T> R<T> success(T Object ){
        R<T>  r = new R<T>();
        r.data = Object;
        r.code = 1;
        return  r;
    }

    public static <T> R<T> error(String msg){
        R<T>  r = new R<T>();
        r.code = 0;
        r.msg = msg;
        return  r;
    }

    public  R<T> add(String key,Object value){
        this.map.put(key,value);
        return  this;
    }
}
