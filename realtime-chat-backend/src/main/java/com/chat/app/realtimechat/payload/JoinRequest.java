package com.chat.app.realtimechat.payload;

import lombok.Data; // For automatic getters/setters
import jakarta.validation.constraints.NotBlank; // For basic validation

/**
 * JoinRequest DTO (Data Transfer Object)
 * This class represents the data expected from the client when a user tries to join the chat.
 * It will typically be sent as a JSON payload in the request body.
 */

@Data // From Lombok: Automatically generates getters, setters, toString, equals, and hashCode methods
public class JoinRequest {

    @NotBlank(message = "Username cannot be empty") // Ensures the 'username' field is not null, empty, or just whitespace
    private String username; // The username provided by the client when joining
}
