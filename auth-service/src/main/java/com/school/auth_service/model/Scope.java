package com.school.auth_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.common_library.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Scope extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String type;
    String value;
    @Builder.Default
    boolean deleted = false;

    @ManyToMany(mappedBy = "scopes")
    @JsonIgnore
    Set<User> users;
}
