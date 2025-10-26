package com.chat.app.realtimechat.config;

import com.chat.app.realtimechat.payload.ChatMessage;
import com.chat.app.realtimechat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * UserInterceptor
 * Handles STOMP CONNECT frames to track users joining.
 * Now protected against duplicate JOIN notifications.
 */
@Component
public class UserInterceptor implements ChannelInterceptor {

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

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        initializeDependencies();

        // Handle new client connection
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            ArrayList<String> rawUsernames = (ArrayList<String>) accessor.getNativeHeader("username");
            if (rawUsernames != null && !rawUsernames.isEmpty()) {
                String username = rawUsernames.get(0);
                Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
                if (sessionAttributes != null) {
                    sessionAttributes.put("username", username);
                }

                // ✅ Prevent duplicate join events for same user
                if (!userService.isUserActive(username)) {
                    userService.addUser(username);

                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setType(ChatMessage.MessageType.JOIN);
                    chatMessage.setSender(username);
                    chatMessage.setContent(username + " joined the chat");

                    messagingTemplate.convertAndSend("/topic/public", chatMessage);
                } else {
                    System.out.println("⚠️ Duplicate CONNECT ignored for active user: " + username);
                }
            }
        }

        return message;
    }
}
