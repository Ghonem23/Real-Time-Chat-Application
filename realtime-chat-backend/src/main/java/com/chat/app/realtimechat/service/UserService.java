package com.chat.app.realtimechat.service;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * UserService
 * This service is responsible for managing the state of currently active users in the chat.
 * It uses an in-memory, thread-safe Set to store usernames.
 */
@Service // Marks this class as a Spring Service component
public class UserService {
    // Using a thread-safe Set to store active usernames
    // synchronizedSet wraps a HashSet to provide thread-safe operations,
    // which is important in a multi-threaded web application.
    private final Set<String> activeUsers = Collections.synchronizedSet(new HashSet<>());

    /**
     * Adds a username to the set of active users.
     * @param username The username to add.
     */
    public void addUser(String username) {
        activeUsers.add(username);
        System.out.println("User added: " + username + ". Active users: " + activeUsers.size());
    }

    /**
     * Removes a username from the set of active users.
     * This will be used later when a user disconnects from the WebSocket.
     * @param username The username to remove.
     */
    public void removeUser(String username) {
        activeUsers.remove(username);
        System.out.println("User removed: " + username + ". Active users: " + activeUsers.size());
    }
    /**
     * Retrieves an unmodifiable view of the current active users.
     * Returning an unmodifiable set prevents external classes from modifying the list directly.
     * @return A Set of active usernames.
     */
    public Set<String> getActiveUsers() {
        return Collections.unmodifiableSet(activeUsers);
    }

    /**
     * Checks if a user is currently active.
     * @param username The username to check.
     * @return true if the user is active, false otherwise.
     */
    public boolean isUserActive(String username) {
        return activeUsers.contains(username);
    }
}
