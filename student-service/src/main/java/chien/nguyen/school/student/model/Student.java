package chien.nguyen.school.student.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fullName;

  private String studentCode;

  private String email;

  private String phone;

  private String gender;

  @ManyToOne
  @JoinColumn(name = "school_id", nullable = false)
  private School school;

  @ManyToOne
  @JoinColumn(name = "class_id", nullable = false)
  private ClassRoom classRoom;


  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
