package ru.msu.com.webprak.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Employee implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "employee_id")
    private Long id;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "home_address")
    @NonNull
    private String homeAddress;

    @Column(nullable = false, name = "day_of_birth")
    @NonNull
    private Date dayOfBirth;

    @Column(nullable = false, name = "education")
    @NonNull
    private String education;

    @Column(nullable = false, name = "position")
    @NonNull
    private String position;

    @Column(nullable = false, name = "length_of_service")
    @NonNull
    private Integer lengthOfService;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee other = (Employee) o;
        return Objects.equals(id, other.id)
                && name.equals(other.name)
                && homeAddress.equals(other.homeAddress)
                && dayOfBirth.equals(other.dayOfBirth)
                && education.equals(other.education)
                && position.equals(other.position)
                && lengthOfService.equals(other.lengthOfService);
    }
}
