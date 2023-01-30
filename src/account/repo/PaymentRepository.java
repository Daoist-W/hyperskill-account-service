package account.repo;

import account.data.entities.PaymentEntity;
import account.data.entities.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, PaymentId> {

    PaymentEntity findByPeriodAndEmployeeIgnoreCase(LocalDate period, String employee);

    List<PaymentEntity> findByEmployeeIgnoreCaseOrderByPeriodDesc(String employee);
}
