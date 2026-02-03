package com.school.student_service.model;

import com.school.common_library.BaseEntity;
import com.school.student_service.enums.SchoolStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "schools")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class School extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String code;
    String address;
    String email;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    SchoolStatus status = SchoolStatus.ACTIVE;
    @Builder.Default
    boolean deleted = false;
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    List<Classes> classes;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    List<Student> students;


}
