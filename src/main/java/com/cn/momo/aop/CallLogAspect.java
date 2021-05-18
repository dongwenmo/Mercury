package com.cn.momo.aop;

import com.cn.momo.annotation.AnnotationParse;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.log.pojo.SysLog;
import com.cn.momo.system.log.service.ISysLogService;
import com.cn.momo.system.user.pojo.User;
import com.cn.momo.util.HttpUtil;
import com.cn.momo.util.JsonUtil;
import com.cn.momo.util.ResultUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * 记录日志
 * dongwenmo 2020-08-20
 */
@Order(20)
@Aspect
@Component
public class CallLogAspect {
    @Autowired
    private ISysLogService iSysLogService;

    @Pointcut(value = "@annotation(com.cn.momo.annotation.CallLog)")
    public void callLog() {
    }

    @Around("callLog()")
    public Object isAccessMethod(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        String mothedName = proceedingJoinPoint.getSignature().getName();//目标方法名
        String mothedTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();//目标方法所属类的类名

        Logger logger = LoggerFactory.getLogger(Class.forName(mothedTypeName));
        logger.info("开始 : {} {}", mothedTypeName, mothedName);

        // 打印入参
        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            logger.info("入参 :【{}】 {}", args[i].getClass().getName(), args[i]);
        }
        // 执行耗时
        long startTime = System.currentTimeMillis();
        String result;
        try {
            result = (String) proceedingJoinPoint.proceed();
        } catch (BusinessException e) {
            ResultUtil resultUtil = new ResultUtil();
            result = resultUtil.error(e);
            logger.error("异常 :  {}", e.getMessage());
//            logger.error("异常 :  ", e);
        } catch (Exception e) {
            ResultUtil resultUtil = new ResultUtil();
            result = resultUtil.error(-1, "系统异常");
            logger.error("异常 :  {}", e.getMessage());
//            logger.error("异常 :  ", e);
        }

        long timeLog = System.currentTimeMillis() - startTime;
        logger.info("耗时 : {} ms\t调用地址：{}", timeLog, HttpUtil.getIP());

        // 打印出参
        logger.info("出参 : {}", result);

        // 记录业务量
        SysLog call = new SysLog();
        call.setServiceClass(mothedTypeName + "." + mothedName);
        call.setCallTime(new Date());
        call.setCallLong((int) timeLog);

        // 获取接口注释
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Map<String, String> map = AnnotationParse.callLogParse(method);

        if (map != null) {
            call.setServiceName(map.get("name"));
            call.setServiceDesc(map.get("desc"));
        } else {
            call.setServiceName("");
            call.setServiceDesc("");
        }

        // 获取调用者id
        if (args.length > 0 && User.class.getName().equals(args[0].getClass().getName())) {
            User loginUser = (User) args[0];
            call.setUserId(loginUser.getUserId());
            call.setCallIp(loginUser.getLastIp());
        }

        // 保存日志
        iSysLogService.insertSelective(call);
        return result;
    }
}
