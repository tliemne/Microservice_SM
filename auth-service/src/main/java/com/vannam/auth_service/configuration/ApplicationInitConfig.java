package com.vannam.auth_service.configuration;


import com.vannam.auth_service.constant.PredefinedPermission;
import com.vannam.auth_service.constant.PredefinedRole;
import com.vannam.auth_service.dto.request.RoleRequest;
import com.vannam.auth_service.entity.Permission;
import com.vannam.auth_service.entity.Role;
import com.vannam.auth_service.entity.User;
import com.vannam.auth_service.enums.DataScope;
import com.vannam.auth_service.enums.Status;
import com.vannam.auth_service.mapper.RoleMapper;
import com.vannam.auth_service.repository.PermissionRepository;
import com.vannam.auth_service.repository.RoleRepository;
import com.vannam.auth_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, RoleMapper roleMapper) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                for(String permission: PredefinedPermission.ADMIN_PERMISSION){

                    permissionRepository.save(Permission.builder()
                            .name(permission)
                            .description(permission)
                        .build());
                }

                Role role=Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("ADMIN FULL ACCESS")
                        .build();

                Set<Permission> permissions=permissionRepository.findAllByNameIn(PredefinedPermission.ADMIN_PERMISSION);
                role.setPermissions(permissions);

                roleRepository.save(role);

                Role roleStudent=Role.builder()
                        .name(PredefinedRole.STUDENT_ROLE)
                        .description("VIEW DATA")
                        .build();

                Set<Permission> permissionsStudent=permissionRepository.findAllByNameIn(PredefinedPermission.STUDENT_PERMISSION);
                roleStudent.setPermissions(permissionsStudent);
                roleRepository.save(roleStudent);

                Role roleManager=Role.builder()
                        .name(PredefinedRole.SCHOOL_MANAGER_ROLE)
                        .description("CRUD SCHOOL CLASS STUDENT")
                        .build();

                Set<Permission> permissionsManager=permissionRepository.findAllByNameIn(PredefinedPermission.SCHOOL_MANAGER_PERMISSION);
                roleManager.setPermissions(permissionsManager);
                roleRepository.save(roleManager);



                User user = User.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .status(Status.ACTIVE)
                        .role(role)
                        .dataScopes(Set.of(DataScope.ALL))
                    .build();


                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
