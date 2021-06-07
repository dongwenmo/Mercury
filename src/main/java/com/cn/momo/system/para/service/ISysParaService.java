package com.cn.momo.system.para.service;

import com.cn.momo.common.IBaseService;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.para.pojo.SysPara;

import java.util.List;

/**
*
* auto 2021-01-28 12:48:06
*/
public interface ISysParaService extends IBaseService<SysPara> {
    /**
     * 获取系统参数所有的组
     * dongwenmo 2021-02-01
     */
    List<String> getSysParaGroups() throws BusinessException;
}
