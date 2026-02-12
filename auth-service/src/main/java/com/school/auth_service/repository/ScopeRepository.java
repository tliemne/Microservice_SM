package com.school.auth_service.repository;

import com.school.auth_service.model.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScopeRepository extends JpaRepository<Scope, String> {
    Optional<Scope> findByTypeAndValueAndDeletedFalse(String type, String value);
}
