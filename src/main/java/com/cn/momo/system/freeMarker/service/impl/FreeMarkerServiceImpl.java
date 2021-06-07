package com.cn.momo.system.freeMarker.service.impl;

import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.freeMarker.dto.GenaSysFileDTO;
import com.cn.momo.system.freeMarker.pojo.Column;
import com.cn.momo.system.freeMarker.service.IFreeMarkerService;
import com.cn.momo.system.freeMarker.util.FreeMarker;
import com.cn.momo.util.CheckUtil;
import com.cn.momo.util.DateUtil;
import com.cn.momo.util.FileUtil;
import com.cn.momo.util.StringUtil;
import com.cn.momo.util.sql.SQL;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dongwenmo 2021-06-07
 */
@Service
public class FreeMarkerServiceImpl implements IFreeMarkerService {
    /**
     * 生成系统文件，controller/service/serviceImpl/mapper/pojo
     * dongwenmo 2021-06-07
     */
    @Override
    public void genaSysFile(GenaSysFileDTO genaSysFileDTO) throws BusinessException {
        String templatePath, defaultProjectPath, aimFilePath, templateName, packagePath;
        String dsName = genaSysFileDTO.getDsName();// 数据源名
        String dbName = genaSysFileDTO.getDbName();// 数据源名
        String tableName = genaSysFileDTO.getTableName();// 表名
        String className = genaSysFileDTO.getClassName();// 类名
        String modelPath = genaSysFileDTO.getModelPath();// 模块路径

        // 校验入参
        CheckUtil.isNull(dbName, "用户名");
        CheckUtil.isNull(tableName, "表名");
        CheckUtil.isNull(modelPath, "模块路径");

        // 处理参数
        // 类名为空时，表名的驼峰命名就是类名
        if (StringUtil.isNull(className)) {
            className = StringUtil.toUpperCaseFirst(StringUtil.getCamelCase(tableName));
        }
        packagePath = modelPath.replace("\\", ".");// 包路径
        templatePath = StringUtil.getProjectPath() + "\\src\\main\\resources\\templates\\";// 模板路径
        defaultProjectPath = StringUtil.getProjectPath() + "\\src\\main\\java\\com\\cn\\momo\\";// 项目默认路径
        aimFilePath = defaultProjectPath + modelPath;// 目标文件路径

        // 生成文件
        // 1.生成controller
        this.genaControllerTmpl(templatePath, aimFilePath, className, packagePath);
        // 2.生成service
        this.genaServiceTmpl(templatePath, aimFilePath, className, packagePath);
        // 3.生成serviceImpl
        this.genaServiceImplTmpl(templatePath, aimFilePath, className, packagePath);
        // 4.生成mapper
        this.genaMapperTmpl(templatePath, aimFilePath, className, packagePath);
        // 5.生成pojo
        this.genaPojoTmpl(templatePath, aimFilePath, className, packagePath, tableName, dbName, dsName);
    }

    private void genaControllerTmpl(String templatePath, String aimFilePath, String className, String packagePath) throws BusinessException {
        // 创建文件夹
        aimFilePath = aimFilePath + "\\controller\\";
        FileUtil.isChartPathExist(aimFilePath);
        aimFilePath = aimFilePath + className + "Controller.java";
        FileUtil.deleteIfExists(aimFilePath);

        // 数据模型
        Map<String, Object> map = new HashMap<>();
        map.put("path1", packagePath);// 包路径
        map.put("className", className);// 类名
        map.put("curTime", DateUtil.format("yyyy-MM-dd"));// 当前时间

        // 生成文件
        FreeMarker.generateJavaFile(templatePath, "ObjControllerTmpl.ftl", map, aimFilePath);
    }

    private void genaServiceTmpl(String templatePath, String aimFilePath, String className, String packagePath) throws BusinessException {
        // 创建文件夹
        aimFilePath = aimFilePath + "\\service\\";
        FileUtil.isChartPathExist(aimFilePath);
        aimFilePath = aimFilePath + "I" + className + "Service.java";
        FileUtil.deleteIfExists(aimFilePath);

        // 数据模型
        Map<String, Object> map = new HashMap<>();
        map.put("path1", packagePath);// 包路径
        map.put("className", className);// 类名
        map.put("curTime", DateUtil.format("yyyy-MM-dd"));// 当前时间

        // 生成文件
        FreeMarker.generateJavaFile(templatePath, "IObjServiceTmpl.ftl", map, aimFilePath);
    }

    private void genaServiceImplTmpl(String templatePath, String aimFilePath, String className, String packagePath) throws BusinessException {
        // 创建文件夹
        aimFilePath = aimFilePath + "\\service\\impl\\";
        FileUtil.isChartPathExist(aimFilePath);
        aimFilePath = aimFilePath + className + "ServiceImpl.java";
        FileUtil.deleteIfExists(aimFilePath);

        // 数据模型
        Map<String, Object> map = new HashMap<>();
        map.put("path1", packagePath);// 包路径
        map.put("className", className);// 类名
        map.put("curTime", DateUtil.format("yyyy-MM-dd"));// 当前时间

        // 生成文件
        FreeMarker.generateJavaFile(templatePath, "ObjServiceImplTmpl.ftl", map, aimFilePath);
    }

    private void genaMapperTmpl(String templatePath, String aimFilePath, String className, String packagePath) throws BusinessException {
        // 创建文件夹
        aimFilePath = aimFilePath + "\\mapper\\";
        FileUtil.isChartPathExist(aimFilePath);
        aimFilePath = aimFilePath + className + "Mapper.java";
        FileUtil.deleteIfExists(aimFilePath);

        // 数据模型
        Map<String, Object> map = new HashMap<>();
        map.put("path1", packagePath);// 包路径
        map.put("className", className);// 类名
        map.put("curTime", DateUtil.format("yyyy-MM-dd"));// 当前时间

        // 生成文件
        FreeMarker.generateJavaFile(templatePath, "ObjMapperTmpl.ftl", map, aimFilePath);
    }

    private void genaPojoTmpl(String templatePath, String aimFilePath, String className, String packagePath, String tableName, String dbName, String dsName) throws BusinessException {
        SQL sql = new SQL(dsName);
        sql.addSql("");

        // 创建文件夹
        aimFilePath = aimFilePath + "\\pojo\\";
        FileUtil.isChartPathExist(aimFilePath);
        aimFilePath = aimFilePath + className + ".java";
        FileUtil.deleteIfExists(aimFilePath);

        // 数据模型
        Map<String, Object> map = new HashMap<>();
        map.put("path1", packagePath);// 包路径
        map.put("className", className);// 类名
        map.put("curTime", DateUtil.format("yyyy-MM-dd"));// 当前时间
        map.put("tableName", tableName);// 数据库表名
        map.put("columns", getColumns(dsName, dbName, tableName));// 数据库信息

        // 生成文件
        FreeMarker.generateJavaFile(templatePath, "ObjTmpl.ftl", map, aimFilePath);
    }

    private List<Column> getColumns(String dsName, String dbName, String tableName) throws BusinessException {
        SQL sql = new SQL(dsName);
        sql.clear();
        sql.addSql("  select table_schema,table_name,lower(column_name) column_name,data_type mysql_type,column_type,  ");
        sql.addSql("         column_comment                                                                            ");
        sql.addSql("    from information_schema.columns                                                                ");
        sql.addSql("   where table_schema = ?                                                                          ");
        sql.addSql("     and table_name = ?                                                                            ");
        sql.setPara(dbName, tableName);
        List<Map<String, Object>> requestList = sql.query();

        List<Column> columns = new ArrayList<>();
        for (Map map : requestList) {
            Column column = new Column();
            column.setTableSchema((String) map.get("table_schema"));
            column.setTableName((String) map.get("table_name"));
            column.setColumnComment((String) map.get("column_comment"));
            column.setColumnType((String) map.get("column_type"));
            column.setMysqlType((String) map.get("mysql_type"));
            String colunmName = (String) map.get("column_name");
            column.setColumnName(StringUtil.getCamelCase(colunmName));

            columns.add(column);
        }

        return columns;
    }
}
