package com.pet.hospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket 核心配置类
 */
@Configuration
public class WebSocketConfig {
    
    /**
     * 该 Bean 会自动注册使用了 @ServerEndpoint 注解的 websocket endpoint
     * 注意：如果使用的是 Spring Boot 内置的 Tomcat 容器，则必须注入该 Bean；如果打成 war 包部署在外置 Tomcat 则无需注入
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
