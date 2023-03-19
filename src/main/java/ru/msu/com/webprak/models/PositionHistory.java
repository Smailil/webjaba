package ru.msu.com.webprak.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "position_history")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class PositionHistory implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "employee_id")
    @ToString.Exclude
    @NonNull
    private Employee employee;

    @Column(nullable = false, name = "position")
    @NonNull
    private String position;

    @Column(nullable = false, name = "date_of_attainment")
    @NonNull
    private Date dateOfAttainment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionHistory other = (PositionHistory) o;
        return Objects.equals(id, other.id)
                && employee.equals(other.employee)
                && position.equals(other.position)
                && dateOfAttainment.equals(other.dateOfAttainment);
    }

}