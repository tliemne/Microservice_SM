package com.vannam.auth_service.service;


import com.vannam.auth_service.dto.request.RoleRequest;
import com.vannam.auth_service.dto.request.UserRequest;
import com.vannam.auth_service.dto.response.UserResponse;
import com.vannam.auth_service.dto.response.UserDetailResponse;
import com.vannam.auth_service.dto.response.UserResponse;
import com.vannam.auth_service.entity.RefreshToken;
import com.vannam.auth_service.entity.Role;
import com.vannam.auth_service.entity.User;
import com.vannam.auth_service.exception.AppException;
import com.vannam.auth_service.exception.ErrorCode;
import com.vannam.auth_service.mapper.UserMapper;
import com.vannam.auth_service.repository.RefreshTokenRepository;
import com.vannam.auth_service.repository.RoleRepository;
import com.vannam.auth_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RefreshTokenRepository refreshTokenRepository;

    public UserResponse createUser(UserRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        Role role =roleRepository.findRoleByName(request.getRoleName()).orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_EXISTED));
        user.setRole(role);
        user.setDataScopes(new HashSet<>(request.getDataScopes()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(Long id, UserRequest update){
        User user=userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.update(update,user);
        userRepository.save(user);
        return userMapper.toUserResponse(user);

    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }


    public UserResponse getById(Long id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserDetailResponse getUserDetail(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserDetailResponse(user);
    }

    @Transactional
    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        refreshTokenRepository.deleteByUser_Id((id));

        userRepository.delete(user);
    }

}
