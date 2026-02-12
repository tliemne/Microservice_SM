package com.school.auth_service.service;

import com.school.common_library.exception.*;
import com.school.auth_service.dto.response.UserInternalResponse;
import com.school.auth_service.mapper.UserMapper;
import com.school.auth_service.model.Role;
import com.school.auth_service.model.User;
import com.school.auth_service.repository.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInternalService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;

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

    public boolean existsById(String userId){
        return userRepository.existsById(userId);
    }




}
