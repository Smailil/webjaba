package ru.msu.com.webprak.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employees_on_projects")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeOnProject implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "project_id")
    @NonNull
    private Long projectId;

    @Column(nullable = false, name = "employee_id")
    @NonNull
    private Long employeeId;

    @Column(nullable = false, name = "role")
    @NonNull
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeOnProject other = (EmployeeOnProject) o;
        return Objects.equals(id, other.id)
                && projectId.equals(other.projectId)
                && employeeId.equals(other.employeeId)
                && role.equals(other.role);
    }
}