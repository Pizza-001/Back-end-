package com.pet.hospital.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AI 问诊室 WebSocket 端点
 * 用于向前端模拟流式数据（打字机效果）推送
 */
@Slf4j
@ServerEndpoint("/ai/chat")
@Component
public class AiChatEndpoint {

    // 存储所有连接的 session
    private static final ConcurrentHashMap<String, Session> SESSION_POOL = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        SESSION_POOL.put(session.getId(), session);
        log.info("【AI问诊室】有新连接加入: {}，当前在线人数: {}", session.getId(), SESSION_POOL.size());
        sendMessage(session, "【系统提示】你好！我是宠物医院的 AI 智能助手，可以为你解答养宠相关的简单问题。");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("【AI问诊室】收到客户端 {} 消息: {}", session.getId(), message);
        
        // 模拟非阻塞调用 AI 大模型并进行流式字符推送
        new Thread(() -> {
            String mockAiResponse = "针对您提到的情况，可能是宠物近期受到应激影响或受凉，建议您保持观察，如果持续食欲不佳请尽快使用【预约挂号】找专业医生面诊哦。";
            for (char c : mockAiResponse.toCharArray()) {
                try {
                    // 模拟大模型 token 生成的延迟
                    Thread.sleep(80);
                    sendMessage(session, String.valueOf(c));
                } catch (InterruptedException e) {
                    log.error("AI 模拟线程中断", e);
                }
            }
        }).start();
    }

    @OnClose
    public void onClose(Session session) {
        SESSION_POOL.remove(session.getId());
        log.info("【AI问诊室】连接断开: {}，当前在线人数: {}", session.getId(), SESSION_POOL.size());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("【AI问诊室】发生错误: {}", session.getId(), error);
    }

    private void sendMessage(Session session, String message) {
        try {
            if (session.isOpen()) {
                // 使用 getBasicRemote() 保证发送的同步性
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            log.error("WebSocket 消息推送异常", e);
        }
    }
}
