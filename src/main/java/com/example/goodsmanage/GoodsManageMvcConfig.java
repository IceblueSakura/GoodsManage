package com.example.goodsmanage;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 用于处理登录事件的拦截器
 */

@Configuration
public class GoodsManageMvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {  // 允许跨域请求，方便使用其他工具测试post/get方法
//        registry.addMapping("/**").allowedOrigins("*")  // 在所有地址下允许所有来源(如其他网页)的请求
//                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")  // 允许所有HTTP请求(尽管只用GET/POST/OPTIONS)
//                .allowCredentials(true).maxAge(3600);
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandleInterceptor())  // 过滤器使用LoginHandleInterceptor这个自定义类
                .addPathPatterns("/**")  // 需要拦截的地址，设置为全部页面
                .excludePathPatterns("/","/user","/user/login","/file/upload","/user/register");  // 不经过拦截器处理直接放行的地址
    }
}





