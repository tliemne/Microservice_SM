package com.school.auth_service.service;

import com.school.common_library.exception.*;
import com.school.auth_service.dto.request.UserCreationRequest;
import com.school.auth_service.dto.request.UserUpdateRequest;
import com.school.auth_service.dto.response.UserInternalResponse;
import com.school.auth_service.dto.response.UserResponse;
import com.school.auth_service.mapper.UserMapper;
import com.school.auth_service.model.Role;
import com.school.auth_service.model.User;
import com.school.auth_service.repository.*;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USERNAME_EXISTED);

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<Role>();
        roleRepository.findById("STUDENT").ifPresent(roles::add);
        if (roles.isEmpty()) throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            var roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    public UserResponse deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setDeleted(true);
        userRepository.save(user);
        log.warn("user {} has been soft deleted", userId);
        userRepository.deleteById(userId);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse restore(String userId) {
        User user = userRepository.findByIdAndDeletedTrue(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setDeleted(false);
        userRepository.save(user);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse updateRoleForUser(String userId, String roleName) {
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ADMIN"));
        if (isAdmin) {
            throw new AppException(ErrorCode.CANNOT_UPDATE_ADMIN);
        }

        Role newRole = roleRepository.findByNameAndDeletedFalse(roleName)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        if ("ADMIN".equals(roleName)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        user.getRoles().clear();
        user.getRoles().add(newRole);

        log.info("Cập nhật Role cho User {}: {} -> {}", user.getUsername(), "STUDENT", roleName);

        return userMapper.toUserResponse(userRepository.save(user));
    }



    public UserInternalResponse getInternalUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return UserInternalResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .build();
    }




}
