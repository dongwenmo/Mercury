package com.cn.momo.system.para.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
*
* auto 2021-01-28 12:48:06
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sys_para")
public class SysPara {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paraId;// 系统参数id
	private String paraGroup;// 组
	private String paraKey;// 键
	private String paraValue;// 值
	private String paraDesc;// 描述
}
