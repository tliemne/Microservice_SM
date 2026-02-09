package com.school.auth_service.repository;

import com.school.auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String name);

    Optional<User> findByIdAndDeletedTrue(String userId);

    Optional<User> findByIdAndDeletedFalse(String userId);
}
