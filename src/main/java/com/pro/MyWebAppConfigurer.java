package com.pro;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源映射路径
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {

	private static String path=ProductDispayApplication.class.getResource("").getPath();
    private static String TEMP_PATH = path.substring(1,path.indexOf("classes"))+"tempFile/";
    
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.err.println("-------"+TEMP_PATH);
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+TEMP_PATH);
        
    }
}
