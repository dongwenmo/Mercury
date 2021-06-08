package com.cn.momo.util;

import com.cn.momo.config.ErrorConfig;
import com.cn.momo.exception.BusinessException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * dongwenmo 2021-05-15
 */
public class HttpUtil {
    /**
     * dongwenmo 2021-05-17
     */
    public static HttpServletRequest getRequest() throws BusinessException {
        try {
            return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        } catch (Exception e) {
            throw new BusinessException(ErrorConfig.ERR_10003, "获取HttpServletRequest失败");
        }
    }

    /**
     * 通过request获取IP
     * dongwenmo 2021-05-15
     */
    public static String getIP() throws BusinessException {
        return getIP(getRequest());
    }

    /**
     * 通过request获取IP
     * dongwenmo 2021-05-15
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
}
