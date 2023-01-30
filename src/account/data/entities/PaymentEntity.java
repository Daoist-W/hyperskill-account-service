package account.data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@IdClass(PaymentId.class)
public class PaymentEntity {
    @Id
    LocalDate period;
    @Id
    String employee;
    Long salary;

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public LocalDate getPeriod() {
        return period;
    }

    public void setPeriod(LocalDate period) {
        this.period = period;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentEntity)) return false;
        PaymentEntity payment = (PaymentEntity) o;
        return Objects.equals(employee, payment.employee)
                && Objects.equals(period, payment.period)
                && Objects.equals(salary, payment.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, period, salary);
    }
}
