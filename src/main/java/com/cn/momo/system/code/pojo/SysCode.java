package com.cn.momo.system.code.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
*
* auto 2021-02-02 09:58:16
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sys_code")
public class SysCode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codeId;// 字典id
	private String codeGroup;// 组
	private String codeKey;// 键
	private String name;// 键名
	private String codeValue;// 入库值
	private String content;// 显示内容
	private Integer priority;// 序号
}
