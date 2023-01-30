package account.services;

import account.data.dtos.Payment;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PaymentService {

    Map<String, String> uploadPayrolls(List<Payment> payments) throws Exception;

    Map<String, String> changeSalary(Payment payment) throws Exception;

    Object getEmployeePayByPeriod(String period, String email) throws Exception;

    List<Payment> getEmployeePayForAllPeriodsDesc(String email) throws Exception;

}
