package com.devkk.micro.framework.interceptor;

import com.devkk.micro.common.CacheModuleEnum;
import com.devkk.micro.framework.cache.CacheService;
import com.devkk.micro.framework.common.GlobalResultCode;
import com.devkk.micro.framework.exception.BusinessException;
import com.devkk.micro.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author zhongkunming
 */
@Component
@RequiredArgsConstructor
public class GlobalInterceptor implements HandlerInterceptor {

    private final CacheService cacheService;

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("_af", Boolean.TRUE);
        String token = JwtUtils.getToken(request);
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(GlobalResultCode.ILLEGALITY_TOKEN_ERROR);
        }
        String userId = JwtUtils.getUserId(request);
        if (!StringUtils.equals(userId, cacheService.get(CacheModuleEnum.TOKEN, token))) {
            throw new BusinessException(GlobalResultCode.LOGIN_EXPIRED_ERROR);
        }
        cacheService.ttl(CacheModuleEnum.TOKEN, token);
        request.setAttribute("_aaf", Boolean.TRUE);
        return true;
    }
}
