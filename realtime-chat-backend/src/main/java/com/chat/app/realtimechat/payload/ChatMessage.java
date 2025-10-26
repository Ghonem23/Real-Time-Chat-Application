package com.chat.app.realtimechat.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * ChatMessage DTO
 * Represents a message sent over the WebSocket.
 */
@Getter
@Setter
public class ChatMessage {

    // Enum to classify the type of message (e.g., standard chat, user joining/leaving)
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    private MessageType type;
    private String content; // The actual text message
    private String sender;  // The username of the sender
    private String timestamp; // Timestamp when the message was created
}
