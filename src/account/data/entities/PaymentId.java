package account.data.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class PaymentId implements Serializable {
    private LocalDate period;
    private String employee;

    public PaymentId() {
    }

    public PaymentId(LocalDate period, String employee) {
        this.period = period;
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentId)) return false;
        PaymentId paymentId = (PaymentId) o;
        return Objects.equals(period, paymentId.period) && Objects.equals(employee, paymentId.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, employee);
    }
}
