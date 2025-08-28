package com.tfg.koeshiru.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



    @Configuration
    public class ImageConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            // "file:" + ruta relativa desde el directorio base donde ejecutas la app
            registry.addResourceHandler("/images/**")
                    .addResourceLocations("file:images/");
        }


    }

