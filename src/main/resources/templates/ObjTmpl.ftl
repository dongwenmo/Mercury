package com.cn.momo.${path1}.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
<#list columns as column>
	<#if column.javaType = "Date">
		import java.util.Date;<#break >
	</#if>
</#list>

/**
 *
 * auto ${curTime}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "${tableName}")
public class ${className} {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
<#list columns as column>
	private ${column.javaType} ${column.columnName};// ${column.columnComment}
</#list>

}