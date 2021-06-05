package com.cn.momo.util;

import com.cn.momo.util.sql.config.DBConfig;
import com.cn.momo.config.ErrorConfig;
import com.cn.momo.exception.BusinessException;

import java.util.List;
import java.util.Map;

/**
 * @author dongwenmo
 * @create 2020-08-17 12:34
 */
public class CheckUtil {
    // 该参数为空时，抛出异常
    public static void isNull(String param, String desc) throws BusinessException {
        if (param == null || "".equals(param)) {
            throw new BusinessException(ErrorConfig.ERR_11001, desc + "不允许为空！");
        }
    }

    // 该参数为空时，抛出异常
    public static void isNull(Integer param, String desc) throws BusinessException {
        if (param == null) {
            throw new BusinessException(ErrorConfig.ERR_11001, desc + "不允许为空！");
        }
    }

    /**
     * 校验入参在某几个值中
     * dongwenmo 2021-01-22
     */
    public static void isIn(String param, String desc, String... args) throws BusinessException {
        // 非空校验
        isNull(param, desc);

        boolean flag = false;
        for (String i : args) {
            if (param.equals(i)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new BusinessException(desc + "不存在值为【" + param + "】的选项，请检查！");
        }
    }

    /**
     * 校验入参是否在数据字典中
     * dongwenmo 2021-03-30
     */
    public static void isInCode(String param, String desc, String code, String group) throws BusinessException {
        StringBuffer sqlBF = new StringBuffer();

        sqlBF.setLength(0);
        sqlBF.append("  select code_value from sys_code where code_group = ? and code_key = ?  ");
        List<Map<String, Object>> requestList = DBUtil.query(DBConfig.LOCALHOST, sqlBF.toString(),
                group, code);

        int size = requestList.size();
        if (size == 0) {
            throw new BusinessException("根据字典编号【" + group + "】【" + code + "】未获取到相应的值，请检查");
        }
        String[] codes = new String[size];
        for (int i = 0; i < size; i++) {
            codes[i] = (String) requestList.get(i).get("code_value");
        }
        isIn(param, desc, codes);
    }

    /**
     * 校验入参是否在数据字典中
     * dongwenmo 2021-03-30
     */
    public static void isInCode(String param, String desc, String code) throws BusinessException {
        isInCode(param, desc, code, "default");
    }
}
