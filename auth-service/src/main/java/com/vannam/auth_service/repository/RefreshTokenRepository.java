package com.vannam.auth_service.repository;

import com.vannam.auth_service.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);

    RefreshToken findByUser_Id(Long userId);

    void deleteByUser_Id(Long userId);

    void deleteByToken(String token);
}
