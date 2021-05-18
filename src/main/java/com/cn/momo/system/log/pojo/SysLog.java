package com.cn.momo.system.log.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统日志
 * dongwenmo 2021-05-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sys_log")
public class SysLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer logId;// 调用日志id
	private String serviceName;// 服务名称
	private String serviceDesc;// 服务描述
	private String serviceClass;// 服务地址
	private Integer userId;// 用户id
	private Date callTime;// 调用时间
	private Integer callLong;// 调用时长
	private String callIp;// 调用ip
}
