package com.cn.momo.system.freeMarker.service;

import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.freeMarker.dto.GenaSysFileDTO;

/**
 * dongwenmo 2021-06-07
 */
public interface IFreeMarkerService {
    /**
     * 生成系统文件，controller/service/serviceImpl/mapper/pojo
     * dongwenmo 2021-06-07
     */
    void genaSysFile(GenaSysFileDTO genaSysFileDTO) throws BusinessException;
}
