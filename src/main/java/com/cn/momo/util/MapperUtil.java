package com.cn.momo.util;

import com.cn.momo.exception.BusinessException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapperUtil<T> {
    /**
     * List<map> mapper 装载为List<?>
     */
    public List<T> mapperList(List<Map<String,Object>> maplist, Class<T> t) throws BusinessException {
        List<T> rtnlist=new ArrayList<>();
        if(maplist==null||maplist.size()==0){
            return rtnlist;
        }
        try {
            for(Map<String,Object> map:maplist){
                T tobj=t.newInstance();
                for(Object key:map.keySet()){
                    String keyStr = StringUtil.getCamelCase((String)key);
                    Field field =t.getDeclaredField(keyStr);
                    field.setAccessible(true);
                    field.set(tobj, map.get(key));
                }
                rtnlist.add(tobj);
            }
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }

        return rtnlist;
    }
    /**
     * 反射Mapper  数据对象
     */
    public <T>T  mapperObj(Map map,Class<T> t) throws BusinessException{
        try {
            if(map==null||map.size()==0){
                return t.newInstance();
            }
            Object tobj=t.newInstance();
            for(Object key:map.keySet()){
                String keyStr = StringUtil.getCamelCase((String)key);
                Field field =t.getDeclaredField(keyStr);
                field.setAccessible(true);
                field.set(tobj, map.get(key));
            }
            return (T) tobj;
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

}
