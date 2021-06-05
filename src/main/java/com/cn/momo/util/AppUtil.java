package com.cn.momo.util;

import com.cn.momo.util.sql.config.DBConfig;
import com.cn.momo.exception.BusinessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppUtil {

    // 根据代码编号获取字典对应关系
    public static List<Map<String, Object>> getCode(String dmbh) throws BusinessException {
        StringBuffer sqlBF = new StringBuffer();

        sqlBF.setLength(0);
        sqlBF.append("  select code,content  ");
        sqlBF.append("    from mineark2.code ");
        sqlBF.append("   where dmbh = ?      ");
        sqlBF.append("     and dbid is null  ");
        sqlBF.append("   order by xh         ");

        List<Map<String, Object>> requestList = DBUtil.query(DBConfig.LOCALHOST, sqlBF.toString(),
                dmbh);

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
        StringBuffer sqlBF = new StringBuffer();

        sqlBF.setLength(0);
        sqlBF.append("  select code,content  ");
        sqlBF.append("    from mineark2.code ");
        sqlBF.append("   where dmbh = ?      ");
        sqlBF.append("     and code = ?      ");
        sqlBF.append("     and dbid is null  ");
        sqlBF.append("   order by xh         ");

        List<Map<String, Object>> requestList = DBUtil.query(DBConfig.LOCALHOST, sqlBF.toString(),
                dmbh, code);
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
        StringBuffer sqlBF = new StringBuffer();

        sqlBF.setLength(0);
        sqlBF.append("  select para_value    ");
        sqlBF.append("    from sys_para   ");
        sqlBF.append("   where para_key = ? ");


        List<Map<String, Object>> requestList = DBUtil.query(DBConfig.LOCALHOST, sqlBF.toString(),
                csbh);
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
        StringBuffer sqlBF = new StringBuffer();

        sqlBF.setLength(0);
        sqlBF.append("  select content         ");
        sqlBF.append("    from sys_code        ");
        sqlBF.append("   where code_group = ?  ");
        sqlBF.append("     and code_key = ?    ");
        sqlBF.append("     and code_value = ?  ");

        List<Map<String, Object>> requestList = DBUtil.query(DBConfig.LOCALHOST, sqlBF.toString(),
                group, key, value);
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
