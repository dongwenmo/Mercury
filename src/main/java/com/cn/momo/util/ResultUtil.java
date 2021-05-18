package com.cn.momo.util;

import com.cn.momo.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * dongwenmo 2021-05-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultUtil {
    private String msg;
    private int code;
    private Map<String, Object> data = new HashMap<>();

    public String success() {
        return success("");
    }

    public String success(String msg) {
        this.code = 0;
        this.msg = msg;
        return JsonUtil.toJson(this);
    }

    public String error() {
        return error("系统异常");
    }

    public String error(String msg) {
        return error(-1, msg);
    }
    
    public String error(BusinessException e){
        return error(e.getStateFlag(), e.getMessage());
    }

    public String error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return JsonUtil.toJson(this);
    }


    public void put(String key, Object value) {
        this.data.put(key, value);
    }
}
