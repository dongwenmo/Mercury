package com.cn.momo.system.para.service.impl;

import com.cn.momo.common.BaseServiceImpl;
import com.cn.momo.config.DBConfig;
import com.cn.momo.system.para.pojo.SysPara;
import com.cn.momo.system.para.service.ISysParaService;
import com.cn.momo.util.DBUtil;
import com.cn.momo.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * auto 2021-01-28 12:48:06
 */
@Service
public class SysParaServiceImpl extends BaseServiceImpl<SysPara> implements ISysParaService {

    @Override
    public List<String> getSysParaGroups() {
        List<String> list = new ArrayList<>();
        StringBuffer sqlBF = new StringBuffer();

        sqlBF.setLength(0);
        sqlBF.append("  select distinct para_group from sys_para order by para_group  ");

        List<Map<String, Object>> requestList = DBUtil.query(DBConfig.LOCALHOST, sqlBF.toString());
        for (Map<String, Object> i : requestList) {
            list.add((String) i.get("para_group"));
        }

        return list;
    }
}
