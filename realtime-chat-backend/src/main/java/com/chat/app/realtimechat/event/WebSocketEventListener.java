package com.chat.app.realtimechat.event;

import com.chat.app.realtimechat.payload.ChatMessage;
import com.chat.app.realtimechat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * WebSocketEventListener
 * Listens for WebSocket connect and disconnect events.
 * On disconnect, broadcasts a "left" message.
 */
@Component
public class WebSocketEventListener {

    @Autowired
    private ApplicationContext applicationContext;

    private SimpMessageSendingOperations messagingTemplate;
    private UserService userService;

    private void initializeDependencies() {
        if (messagingTemplate == null) {
            messagingTemplate = applicationContext.getBean(SimpMessageSendingOperations.class);
            userService = applicationContext.getBean(UserService.class);
        }
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("✅ A new WebSocket connection established.");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        initializeDependencies();
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {
            System.out.println("❌ User disconnected: " + username);
            userService.removeUser(username);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);
            chatMessage.setContent(username + " left the chat");

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        } else {
            System.out.println("⚠️ Disconnected session without username attribute.");
        }
    }
}
