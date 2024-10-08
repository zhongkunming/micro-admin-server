package com.devkk.micro.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.net.NetUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zhongkunming
 */
public final class WebServeUtils {

    private WebServeUtils() {
    }

    public static String getRemoteIp(final HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("X-Original-Forwarded-For");
            if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
                ip = request.getHeader("x-forwarded-for");
            }
            if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
                ip = request.getRemoteAddr();
                if (StringUtils.equalsAnyIgnoreCase(ip, "127.0.0.1", "0:0:0:0:0:0:0:1")) {
                    ip = getHostIp();
                }
            }
        } catch (Exception ignored) {
        }
        if (StringUtils.isNotBlank(ip) && StringUtils.contains(ip, ",")) {
            ip = StringUtils.substringBefore(ip, ",");
        }
        return ip;
    }

    public static String getHostIp() {
        return NetUtil.getLocalhostStrV4();
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }
}
