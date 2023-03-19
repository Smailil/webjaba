package ru.msu.com.webprak.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "payment_history")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentHistory implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "payment_id")
    private Long id;

    @Column(nullable = false, name = "employee_id")
    @NonNull
    private Long employeeId;

    @Column(nullable = false, name = "type")
    @NonNull
    private String type;

    @Column(nullable = false, name = "payment_amount")
    @NonNull
    private Double paymentAmount;

    @Column(nullable = false, name = "date")
    @NonNull
    private Date date;

    @Column(nullable = false, name = "is_award")
    @NonNull
    private Boolean isAward;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentHistory other = (PaymentHistory) o;
        return Objects.equals(id, other.id)
                && employeeId.equals(other.employeeId)
                && type.equals(other.type)
                && paymentAmount.equals(other.paymentAmount)
                && date.equals(other.date)
                && isAward.equals(other.isAward);
    }
}