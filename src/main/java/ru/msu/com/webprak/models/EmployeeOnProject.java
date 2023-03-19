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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    @ToString.Exclude
    @NonNull
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    @ToString.Exclude
    @NonNull
    private Employee employee;

    @Column(nullable = false, name = "role")
    @NonNull
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeOnProject other = (EmployeeOnProject) o;
        return Objects.equals(id, other.id)
                && project.equals(other.project)
                && employee.equals(other.employee)
                && role.equals(other.role);
    }
}