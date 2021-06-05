package com.cn.momo.test.sql;

import com.cn.momo.exception.BusinessException;
import com.cn.momo.util.JsonUtil;
import com.cn.momo.util.sql.SQL;

/**
 * dongwenmo 2021-06-05
 */
public class TestSql {
    public static void main(String[] args) {
        try {
            select();
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    public static void select() throws BusinessException {
        SQL sql = new SQL();
        sql.clear();
        sql.addSql("  select * from dbms_datasource   ");

        System.out.println(JsonUtil.toJson(sql.query()));
    }
}
