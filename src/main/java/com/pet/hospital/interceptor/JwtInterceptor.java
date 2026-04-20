package com.pet.hospital.interceptor;

import com.pet.hospital.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            if ("test-token".equals(token)) {
                request.setAttribute("userId", 1L); // 开发环境测试用户ID
                return true;
            }

            if (token != null) {
                // Verify and parse token
                io.jsonwebtoken.Claims claims = JwtUtils.parseToken(token);
                
                // Read userId from JWT and set into request for later use in controllers
                Long userId = claims.get("userId", Long.class);
                if (userId != null) {
                    request.setAttribute("userId", userId);
                }
                return true;
            }
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
        }
        
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"Unauthorized\"}");
        return false;
    }
}
