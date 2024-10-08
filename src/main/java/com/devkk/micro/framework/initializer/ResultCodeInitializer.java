package com.devkk.micro.framework.initializer;

import com.devkk.micro.framework.common.ResultCode;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhongkunming
 */
@Component
public class ResultCodeInitializer implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    @SuppressWarnings("NullableProblems")
    public void onApplicationEvent(ApplicationStartedEvent event) {
        try {
            String BASE_PACKAGE = event.getSpringApplication().getMainApplicationClass().getPackageName();
            Set<Class<?>> clazzs = ClassUtil.scanPackageBySuper(BASE_PACKAGE, ResultCode.class);
            Map<Integer, Class<?>> map = new HashMap<>();
            for (Class<?> clazz : clazzs) {
                for (Field field : clazz.getFields()) {
                    if (field.get(clazz) instanceof ResultCode result) {
                        Integer code = result.getCode();
                        if (!map.containsKey(code)) {
                            map.put(code, clazz);
                            continue;
                        }
                        throw new IllegalArgumentException(String.format("code %s already exists", code));
                    }
                }
            }
            map.clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
