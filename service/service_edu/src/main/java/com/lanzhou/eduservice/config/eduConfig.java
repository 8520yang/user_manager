package com.lanzhou.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.lanzhou.eduservice.mapper")
@ComponentScan(basePackages = {"com.lanzhou"})
public class eduConfig {
/**
 * 逻辑删除插件
 */
    @Bean
    public ISqlInjector sqlInjector()
    {
        return new LogicSqlInjector();
    }
    /**
     * 分页插
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

