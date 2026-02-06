package com.vannam.auth_service.entity;

import com.vannam.auth_service.enums.DataScope;
import com.vannam.auth_service.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String username;
    @Column(nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Status status;


    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE ,CascadeType.REFRESH})
    @JoinColumn(name = "role_id",nullable = false)
    Role role;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_data_scope",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "data_scope")
    Set<DataScope> dataScopes;


}
