package com.devkk.micro.framework.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongkunming
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalFilter extends OncePerRequestFilter {

    private final static List<String> IGNORE_CACHE_REQ_URI_LIST = new ArrayList<>();

    private final static List<String> IGNORE_CACHEP_RESP_URI_LIST = new ArrayList<>();

    private final static PathMatcher pathMatcher = new AntPathMatcher();

//    private final SysLogMapper sysLogMapper;

//    private final ObjectMapper objectMapper;

    private final String INBOUND = "_inbound";

    private final String OUTBOUND = "_outbound";

    @Override
    protected void initFilterBean() {
        List<String> ignores = new ArrayList<>();
        ignores.add("/doc.html");
        ignores.add("/favicon.ico");
        ignores.add("/error");
        ignores.add("/swagger-ui/index.html");
        ignores.add("/swagger-ui*/**");
        ignores.add("/webjars/**");
        ignores.add("/v3/api-docs");
        ignores.add("/v3/api-docs.yaml");
        ignores.add("/v3/api-docs/swagger-config");

        IGNORE_CACHE_REQ_URI_LIST.add("/upload");
        IGNORE_CACHE_REQ_URI_LIST.addAll(ignores);

        IGNORE_CACHEP_RESP_URI_LIST.add("/share");
        IGNORE_CACHEP_RESP_URI_LIST.add("/download");
        IGNORE_CACHEP_RESP_URI_LIST.addAll(ignores);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return HttpMethod.OPTIONS.equals(HttpMethod.valueOf(request.getMethod()));
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setAttribute(INBOUND, Instant.now());
        String uri = request.getRequestURI();
        if (log.isDebugEnabled()) {
            log.debug(uri);
        }
        if (useCache(uri, IGNORE_CACHE_REQ_URI_LIST)) {
            request = new RequestWrapperExt(request);
        }

        if (useCache(uri, IGNORE_CACHEP_RESP_URI_LIST)) {
            response = new ContentCachingResponseWrapper(response);
        }
        chain.doFilter(request, response);
        request.setAttribute(OUTBOUND, Instant.now());
//        accessLog(request, response);
        if (response instanceof ContentCachingResponseWrapper cacheResponse) {
            cacheResponse.copyBodyToResponse();
        }
    }

//    private void accessLog(HttpServletRequest request, HttpServletResponse response) {
//        SysLog sysLog = new SysLog();
//        sysLog.setRequestUri(request.getRequestURI());
//        sysLog.setRequestMethod(request.getMethod());
//        sysLog.setRemoteIp(ServeUtils.getRemoteIp(request));
//        sysLog.setHostIp(ServeUtils.getHostIp());
//        try {
//            Map<String, String[]> parameterMap = request.getParameterMap();
//            if (Objects.nonNull(parameterMap) && !parameterMap.isEmpty()) {
//                sysLog.setQueryParam(objectMapper.writeValueAsString(parameterMap));
//            }
//        } catch (Exception ignored) {
//        }
//        if (request instanceof RequestWrapperExt cacheRequest) {
//            sysLog.setResponseBody(cacheRequest.getContentAsString());
//        }
//        if (response instanceof ContentCachingResponseWrapper cacheResponse) {
//            sysLog.setResponseBody(new String(cacheResponse.getContentAsByteArray()));
//        }
//        Instant inbound = (Instant) request.getAttribute(INBOUND);
//        Instant outbound = (Instant) request.getAttribute(OUTBOUND);
//        Duration between = Duration.between(inbound, outbound);
//        sysLog.setCostTime((int) between.toMillis());
//        if (request.getAttribute("_leg") instanceof Boolean flag && flag) {
//            sysLog.setUserId(JwtUtils.getUserId(request));
//        }
//        sysLogMapper.insert(sysLog);
//    }

    private boolean useCache(String uri, List<String> list) {
        return list.stream().noneMatch(val -> pathMatcher.match(val, uri));
    }
}
