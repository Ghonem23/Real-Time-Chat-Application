package com.chat.app.realtimechat.repository;

import com.chat.app.realtimechat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Indicates that this is a Spring Data repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA automatically creates methods based on method names
    // This will find a User by their username
    Optional<User> findByUsername(String username);
}
