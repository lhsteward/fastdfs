package com.fastdfs.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author lihaichao
 * @ClassName ApplicationConfig
 * @description: 配置文件 静态资源
 **/
@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    /**
     * @Title: addViewControllers
     * @Description: 首页
     **/
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/uploadFile.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        WebMvcConfigurer.super.addViewControllers(registry);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }


    /**
     * @Title: addCorsMappings
     * @Description: 跨域问题   CORS 跨域访问支持
     * @param registry : 
     * @return void
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      //允许跨域的请求
                .allowedMethods("*")                 //允许的请求方法类型 如 : GET POST PUT DELETE
                .allowedOrigins("*")                 //允许跨域访问的域名 如 : http://xxxxx.com
                .allowedHeaders("*");                //允许所有的请求header访问，可以自定义设置任意请求头信息，如："X-YAUTH-TOKEN"
    }

}
