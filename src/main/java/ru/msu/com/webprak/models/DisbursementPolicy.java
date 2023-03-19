package ru.msu.com.webprak.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "disbursement_policies")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class DisbursementPolicy implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "policies_id")
    private Long id;

    @Column(nullable = false, name = "type")
    @NonNull
    private String type;

    @Column(nullable = false, name = "payment_amount")
    @NonNull
    private Double paymentAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisbursementPolicy other = (DisbursementPolicy) o;
        return Objects.equals(id, other.id)
                && type.equals(other.type)
                && paymentAmount.equals(other.paymentAmount);
    }
}