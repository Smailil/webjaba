package ru.msu.com.webprak.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "projects")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Project implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "project_id")
    private Long id;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "start_date")
    @NonNull
    private Date startDate;

    @Column(nullable = false, name = "end_date")
    private Date endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project other = (Project) o;
        return Objects.equals(id, other.id)
                && name.equals(other.name)
                && startDate.equals(other.startDate)
                && Objects.equals(endDate, other.endDate);
    }
}