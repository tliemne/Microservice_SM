package com.school.auth_service.service;

import com.school.common_library.exception.*;
import com.school.auth_service.dto.request.RoleRequest;
import com.school.auth_service.dto.response.RoleResponse;
import com.school.auth_service.mapper.RoleMapper;
import com.school.auth_service.model.Role;
import com.school.auth_service.repository.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }

    public RoleResponse restore(String name) {
        Role role = roleRepository.findByNameAndDeletedTrue(name)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        role.setDeleted(false);
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }


}
