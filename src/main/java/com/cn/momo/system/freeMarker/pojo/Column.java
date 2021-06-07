package com.cn.momo.system.freeMarker.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dongwenmo
 * @create 2020-09-05 13:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Column {
	private String tableSchema;// 数据库名
	private String tableName;// 表名
	private String columnName;// 列名
	private String mysqlType;// mysql数据类型
	private String javaType;// java数据类型
	private String columnType;// 字段详细类型
	private String columnComment;// 字段描述

	public void setMysqlType(String mysqlType){
		this.mysqlType = mysqlType;
		String lowerString = mysqlType.toLowerCase();
		if("int".equals(lowerString)){
			this.javaType = "Integer";
		}else if("varchar".equals(lowerString)){
			this.javaType = "String";
		}else if("datetime".equals(lowerString)){
			this.javaType = "Date";
		}else if("longtext".equals(lowerString)){
			this.javaType = "String";
		}else if("bigint".equals(lowerString)){
			this.javaType = "Integer";
		}else if("decimal".equals(lowerString)){
			this.javaType = "Integer";
		}else if("double".equals(lowerString)){
			this.javaType = "double";
		}else if("float".equals(lowerString)){
			this.javaType = "double";
		}else if("char".equals(lowerString)){
			this.javaType = "String";
		}else if("double".equals(lowerString)){
			this.javaType = "double";
		}
	}
}
