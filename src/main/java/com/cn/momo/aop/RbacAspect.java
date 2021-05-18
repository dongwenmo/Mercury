package com.cn.momo.aop;

import com.cn.momo.annotation.AnnotationParse;
import com.cn.momo.config.ErrorConfig;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.user.cache.UserCache;
import com.cn.momo.system.user.dto.UserCacheDTO;
import com.cn.momo.util.HttpUtil;
import com.cn.momo.util.ResultUtil;
import com.cn.momo.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * dongwenmo 2020-07-22
 */

@Order(10)
@Aspect
@Component
public class RbacAspect {
    @Pointcut(value = "@annotation(com.cn.momo.annotation.Permission)")
    public void privilege() {
    }

    @Around("privilege()")
    public Object isAccessMethod(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        //获取访问目标方法
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();

        //得到方法的访问权限
        String methodAccess = AnnotationParse.prmissionParse(method);

        //如果该方法上没有权限注解，直接调用目标方法
        if (methodAccess != null && !"".equals(methodAccess)) {
            //获取当前用户的Token
            ResultUtil result = new ResultUtil();
            try {
                HttpServletRequest request = HttpUtil.getRequest();
                String accessToken = request.getHeader("accessToken");
                if (StringUtil.isNull(accessToken)) {
                    throw new BusinessException(ErrorConfig.ERR_10001, "accessToken不存在");
                }
                UserCacheDTO user = UserCache.get(accessToken);
                if(user == null){
                    throw new BusinessException(ErrorConfig.ERR_10004, "登录已过期");
                }
            }catch (BusinessException e){
                return result.error(e);
            }catch (Exception e){
                return result.error(e.getMessage());
            }
            
        }
        return proceedingJoinPoint.proceed();
    }

}
