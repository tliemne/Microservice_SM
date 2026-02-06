package com.vannam.auth_service.service;

import com.vannam.auth_service.dto.request.RoleRequest;

import com.vannam.auth_service.dto.response.RoleResponse;
import com.vannam.auth_service.entity.Permission;
import com.vannam.auth_service.entity.Role;
import com.vannam.auth_service.exception.AppException;
import com.vannam.auth_service.exception.ErrorCode;
import com.vannam.auth_service.mapper.RoleMapper;
import com.vannam.auth_service.repository.PermissionRepository;
import com.vannam.auth_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse createRole(RoleRequest request){
        if(roleRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        Role role=roleMapper.toRole(request);
        Set<Permission>  permissions=permissionRepository.findAllByNameIn(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);



    }
    public RoleResponse updateRole(Long id, RoleRequest update){

        Role role=roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        roleMapper.update(update,role);
        Set<Permission>  permissions=permissionRepository.findAllByNameIn(update.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);

    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }


    public RoleResponse getById(Long id) {
        return roleMapper.toRoleResponse(
                roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)));
    }





    public void deleteRole(Long id){
        Role role =roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        roleRepository.delete(role);
    }
}
