package com.cn.momo.system.code.service;

import com.cn.momo.common.IBaseService;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.code.pojo.SysCode;

import java.util.List;
import java.util.Map;

/**
*
* auto 2021-02-02 09:58:16
*/
public interface ISysCodeService extends IBaseService<SysCode> {
    /**
     * 获取数据字典名称列表
     * dongwenmo 2021-02-02
     */
    List<Map<String,Object>> getSysCodeNames(SysCode sysCode);

    /**
     * 获取数据字典组列表
     * dongwenmo 2021-02-02
     */
    List<String> getGroups();

    /**
     * 数据字典移动
     * dongwenmo 2021-02-03
     */
    void move(int codeId, String moveType) throws BusinessException;

    /**
     * 排序
     * dongwenmo 2021-02-03
     */
    List<SysCode> sort(List<SysCode> list);
}
