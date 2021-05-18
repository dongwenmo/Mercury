package com.cn.momo.aop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author dongwenmo
 * @create 2020-04-23 12:21
 */
//@Aspect
//@Component
public class ServiceAspect {

	public static String prefix = "--";

//	@Pointcut("execution(* com.cn.momo.service..*.*(..))")
	public void point() {
	}

//	@Around("point()")
	public Object doAroud(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Gson gson = new GsonBuilder()
				.serializeNulls()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		String mothedName = proceedingJoinPoint.getSignature().getName();//目标方法名
		String mothedTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();//目标方法所属类的类名

		Logger logger = LoggerFactory.getLogger(Class.forName(mothedTypeName));
		logger.info(prefix + "开始 : {} {}", mothedTypeName, mothedName);

		// 打印入参
		Object[] args = proceedingJoinPoint.getArgs();
		logger.info(prefix + "入参 : {}", gson.toJson(args));

		// 执行耗时
		long startTime = System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed();
		logger.info(prefix + "执行耗时 : {} ms", System.currentTimeMillis() - startTime);

		// 打印出参
		logger.info(prefix + "出参 : {}", gson.toJson(result));

		return result;
	}
}
