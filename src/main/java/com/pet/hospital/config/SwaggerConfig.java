package com.pet.hospital.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI petHospitalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("宠物医院管理系统 API")
                        .description("宠物医院管理系统前后端分离接口文档")
                        .version("v1.0"));
    }
}
