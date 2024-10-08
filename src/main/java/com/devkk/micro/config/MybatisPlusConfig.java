package com.devkk.micro.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author zhongkunming
 */
@Configuration
@MapperScan(basePackages = {
        "com.devkk.micro.web.system.mapper"
})
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor defaultMybatisPlusInterceptor(List<InnerInterceptor> interceptors) {
        MybatisPlusInterceptor plusInterceptor = new MybatisPlusInterceptor();
        interceptors.add(new PaginationInnerInterceptor());
        plusInterceptor.setInterceptors(interceptors);
        return plusInterceptor;
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "createDt", LocalDateTime.class, LocalDateTime.now());
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updateDt", LocalDateTime.class, LocalDateTime.now());
            }
        };
    }

//    @Bean
//    public IdentifierGenerator identifierGenerator(GenerateService generateService) {
//        return entity -> generateService.generateLong(entity.getClass());
//    }
}
