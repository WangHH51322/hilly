package cn.edu.cup.hilly.dataSource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author CodeChap
 * @date 2021-07-09 11:09
 * @description WebMvcConfig
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{
    @Override
    public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(20000);
        configurer.registerCallableInterceptors(timeoutInterceptor());
    }
    @Bean
    public CallableProcessingInterceptor timeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }
}