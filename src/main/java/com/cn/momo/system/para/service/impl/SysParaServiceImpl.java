package com.cn.momo.system.para.service.impl;

import com.cn.momo.common.BaseServiceImpl;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.para.pojo.SysPara;
import com.cn.momo.system.para.service.ISysParaService;
import com.cn.momo.util.sql.SQL;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * auto 2021-01-28 12:48:06
 */
@Service
public class SysParaServiceImpl extends BaseServiceImpl<SysPara> implements ISysParaService {

    @Override
    public List<String> getSysParaGroups() throws BusinessException {
        List<String> list = new ArrayList<>();
        SQL sql = new SQL();

        sql.clear();
        sql.addSql("  select distinct para_group from sys_para order by para_group  ");

        List<Map<String, Object>> requestList = sql.query();
        for (Map<String, Object> i : requestList) {
            list.add((String) i.get("para_group"));
        }

        return list;
    }
}
