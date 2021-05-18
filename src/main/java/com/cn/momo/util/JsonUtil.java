package com.cn.momo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * dongwenmo 2020-12-09
 */
public class JsonUtil {
    private static final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * 对象转json
     * dongwenmo 2020-12-09
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
