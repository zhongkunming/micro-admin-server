package com.devkk.micro.utils;

import com.devkk.micro.config.JwtConfig;
import com.devkk.micro.framework.common.GlobalResultCode;
import com.devkk.micro.framework.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.json.jwt.JWT;
import org.dromara.hutool.json.jwt.JWTValidator;
import org.dromara.hutool.json.jwt.signers.JWTSigner;
import org.dromara.hutool.json.jwt.signers.JWTSignerUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author zhongkunming
 */
@Slf4j
@Component
public class JwtUtils {

    public static final String HEADER_AUTH = "Authorization";

    public static final String PARAM_AUTH = "token";

    public static final String HEADER_AUTH_PREFIX = "Bearer ";

    public static final String TOKEN = "Token";

    public static final String USER_ID = "UserId";

    public static final String NANO_ID = "NanoId";

    private static byte[] JWT_KEY;

    public JwtUtils(JwtConfig config) {
        JwtUtils.JWT_KEY = config.getKey().getBytes(StandardCharsets.UTF_8);
    }

    private static JWTSigner getSigner() {
        return JWTSignerUtil.hs512(JWT_KEY);
    }

    public static String createToken(String userId) {
        return JWT.of()
                .setPayload(USER_ID, userId)
                .setPayload(NANO_ID, System.nanoTime())
                .setSigner(getSigner())
                .sign();
    }

    public static String getToken(HttpServletRequest request) {
        String token = (String) request.getAttribute(TOKEN);
        if (StringUtils.isBlank(token)) {
            token = request.getHeader(HEADER_AUTH);
        }
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(PARAM_AUTH);
        }
        token = StringUtils.removeStart(token, HEADER_AUTH_PREFIX);
        request.setAttribute(TOKEN, token);
        return token;
    }

    public static String getUserId(HttpServletRequest request) {
        String userId = (String) request.getAttribute(USER_ID);
        if (StringUtils.isNotBlank(userId)) {
            return userId;
        }
        String token = getToken(request);
        try {
            JWTValidator.of(token).validateAlgorithm(getSigner());
            userId = JWT.of(token).getPayloads().getStr(USER_ID);
            request.setAttribute(USER_ID, userId);
            return userId;
        } catch (Exception e) {
            throw new BusinessException(e, GlobalResultCode.ILLEGALITY_TOKEN_ERROR);
        }
    }

    public static String getToken() {
        return getToken(WebServeUtils.getRequest());
    }

    public static String getUserId() {
        return getUserId(WebServeUtils.getRequest());
    }
}
