package com.cn.momo.util;

import com.cn.momo.exception.BusinessException;
import com.cn.momo.util.sql.SQL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppUtil {

    // 根据代码编号获取字典对应关系
    public static List<Map<String, Object>> getCode(String dmbh) throws BusinessException {
        SQL sql = new SQL();

        sql.clear();
        sql.addSql("  select code,content  ");
        sql.addSql("    from mineark2.code ");
        sql.addSql("   where dmbh = ?      ");
        sql.addSql("     and dbid is null  ");
        sql.addSql("   order by xh         ");
        sql.setPara(dmbh);
        List<Map<String, Object>> requestList = sql.query();

        if (requestList.size() == 0) {
            throw new BusinessException("未在字典表中维护【" + dmbh + "】的对应关系！");
        }

        return requestList;
    }

    // 根据代码编号获取字典对应关系（map形式）
    public static Map<String, Object> getCodeMap(String dmbh) throws BusinessException {
        List<Map<String, Object>> requestList = getCode(dmbh);
        Map<String, Object> map = new HashMap<>();

        for (Map<String, Object> i : requestList) {
            map.put((String) i.get("code"), i.get("content"));
        }

        return map;
    }

    // 取code中文含义
    public static String discode(String dmbh, String code) throws BusinessException {
        SQL sql = new SQL();

        sql.clear();
        sql.addSql("  select code,content  ");
        sql.addSql("    from mineark2.code ");
        sql.addSql("   where dmbh = ?      ");
        sql.addSql("     and code = ?      ");
        sql.addSql("     and dbid is null  ");
        sql.addSql("   order by xh         ");
        sql.setPara(dmbh, code);
        List<Map<String, Object>> requestList = sql.query();
        if (requestList.size() == 0) {
            throw new BusinessException("discode出错：代码编号【" + dmbh + "】代码值【" + code + "】不存在！");
        }

        return (String) requestList.get(0).get("content");
    }

    // 取code中文含义（不存在则取默认值）
    public static String discode(String dmbh, String code, String defaultStr) {
        try {
            return discode(dmbh, code);
        } catch (BusinessException e) {
            return defaultStr;
        }
    }

    // 取系统参数
    public static String getSystemPara(String csbh) throws BusinessException {
        SQL sql = new SQL();
        sql.clear();
        sql.addSql("  select para_value    ");
        sql.addSql("    from sys_para   ");
        sql.addSql("   where para_key = ? ");
        sql.setPara(csbh);

        List<Map<String, Object>> requestList = sql.query();
        if (requestList.size() == 0) {
            throw new BusinessException("未在参数表中维护【" + csbh + "】的值!");
        }

        return (String) requestList.get(0).get("para_value");
    }

    // 取系统参数
    public static String getSystemPara(String csbh, String defaultStr) {
        try {
            return getSystemPara(csbh);
        } catch (BusinessException e) {
            return defaultStr;
        }
    }

    /**
     * 根据键获取字典表的值
     * dongwenmo 2021-03-16
     */
    public static String getCodeContent(String group, String key, String value) throws BusinessException {
        SQL sql = new SQL();
        sql.clear();
        sql.addSql("  select content         ");
        sql.addSql("    from sys_code        ");
        sql.addSql("   where code_group = ?  ");
        sql.addSql("     and code_key = ?    ");
        sql.addSql("     and code_value = ?  ");
        sql.setPara(group, key, value);
        List<Map<String, Object>> requestList = sql.query();
        if (requestList.size() == 0) {
            throw new BusinessException("未在参数表中维护【" + key + "】【" + value + "】的值!");
        }

        return (String) requestList.get(0).get("content");
    }

    /**
     * 根据键获取字典表的值
     * dongwenmo 2021-03-16
     */
    public static String getCodeContent(String key, String value) throws BusinessException {
        return getCodeContent("default", key, value);
    }
}
