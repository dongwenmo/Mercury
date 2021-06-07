package com.cn.momo.system.freeMarker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 生成系统文件DTO
 * dongwenmo 2021-06-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenaSysFileDTO {
    private String dsName;// 数据源名
    private String dbName;// 用户名
    private String tableName;// 表名
    private String className;// 类名
    private String modelPath;// 模块路径
}
