package com.cn.momo.system.freeMarker.controller;

import com.cn.momo.annotation.CallLog;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.freeMarker.dto.GenaSysFileDTO;
import com.cn.momo.system.freeMarker.service.IFreeMarkerService;
import com.cn.momo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * dongwenmo 2021-06-07
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/freeMarker")
public class FreeMarkerController {
    @Autowired
    private IFreeMarkerService iFreeMarkerService;
    
    @PostMapping("/genaSysFile")
    @CallLog(name = "genaSysFile", desc = "生成系统文件")
    public String genaSysFile(GenaSysFileDTO genaSysFileDTO) throws BusinessException {
        ResultUtil result = new ResultUtil();
        iFreeMarkerService.genaSysFile(genaSysFileDTO);
        return result.success("生成成功");
    }
}
