package com.school.user_service.repository;

import com.school.user_service.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByNameAndDeletedTrue(String name);

    Optional<Role> findByNameAndDeletedFalse(String name);
}
