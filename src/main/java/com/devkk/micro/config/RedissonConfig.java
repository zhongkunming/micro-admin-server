package com.devkk.micro.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author zhongkunming
 */
@Component
public class RedissonConfig implements BeanPostProcessor {

    @Override
    @SuppressWarnings(value = {"NullableProblems"})
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if (bean instanceof Redisson redisson) {
//            Codec codec = redisson.getConfig().getCodec();
//            if (codec instanceof JsonJacksonCodec jacksonCodec) {
//                jacksonCodec.getObjectMapper().registerModule(new JavaTimeModule());
//            }
//            if (codec instanceof JsonJacksonCodec) {
//                Codec jacksonCodec = new JsonJacksonCodec(objectMapper);
//                redisson.getConfig().setCodec(jacksonCodec);
//            }
//        }
//        if (bean instanceof Jackson2ObjectMapperBuilder builder) {
//            try {
//                Class<? extends Jackson2ObjectMapperBuilder> clazz = builder.getClass();
//                Field modulesField = clazz.getDeclaredField("modules");
//                modulesField.setAccessible(true);
//                List<Module> modules = (List<Module>) modulesField.get(builder);
//                modules.add(new JavaTimeModule());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}


