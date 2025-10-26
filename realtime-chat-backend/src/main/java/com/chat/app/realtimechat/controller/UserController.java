package com.chat.app.realtimechat.controller;

import com.chat.app.realtimechat.model.User;
import com.chat.app.realtimechat.payload.JoinRequest;
import com.chat.app.realtimechat.repository.UserRepository;
import com.chat.app.realtimechat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import jakarta.validation.Valid;

/**
 * UserController
 * This REST controller handles user-related operations, specifically the "join chat" functionality.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor // Lombok's @RequiredArgsConstructor automatically generates a constructor for any final fields.
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    // The manual constructor has been removed.
    // Lombok's @RequiredArgsConstructor handles this for us,
    // which simplifies the code and is a common practice in Spring Boot.

    /**
     * Handles POST requests to the /api/users/join endpoint.
     * Allows a user to "join" the chat by providing a username.
     *
     * @param joinRequest The request body containing the username.
     * @return ResponseEntity indicating success or failure.
     */
    @PostMapping("/join")
    public ResponseEntity<String> joinChat(@Valid @RequestBody JoinRequest joinRequest) {
        String username = joinRequest.getUsername();

        // 1. Check if the username is already active (connected to the chat)
        // This is important to prevent multiple active sessions for the same username.
        if (userService.isUserActive(username)) {
            return ResponseEntity.badRequest().body("Username '" + username + "' is already active in another session.");
        }

        // 2. Check if the user exists in the database. If not, create and save a new User.
        // We use orElseGet to either get the existing user or create a new one if not found.
        userRepository.findByUsername(username).orElseGet(() -> {
            User newUser = new User();
            newUser.setUsername(username);
            return userRepository.save(newUser); // Save the new user to the database
        });

        // 3. Add the user to the in-memory list of active users.
        userService.addUser(username);

        // Return a success response
        return ResponseEntity.ok("User '" + username + "' joined the chat successfully and is now active.");
    }

    /**
     * Handles GET requests to the /api/users/active endpoint.
     * Returns a list of all currently active usernames.
     * This is useful for testing and for a client to display who is online.
     *
     * @return ResponseEntity containing a Set of active usernames.
     */
    @GetMapping("/active")
    public ResponseEntity<Set<String>> getActiveUsers() {
        return ResponseEntity.ok(userService.getActiveUsers());
    }
}