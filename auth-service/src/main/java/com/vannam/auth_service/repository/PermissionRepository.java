package com.vannam.auth_service.repository;

import com.vannam.auth_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    Set<Permission> findAllByNameIn(Set<String> name);

    boolean existsByName(String name);
}
