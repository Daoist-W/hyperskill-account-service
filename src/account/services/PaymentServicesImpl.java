package account.services;

import account.data.dtos.Payment;
import account.data.entities.AccountEntity;
import account.data.entities.PaymentEntity;
import account.data.entities.PaymentId;
import account.repo.AccountRepo;
import account.repo.PaymentRepository;
import account.utility.ConversionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("paymentService")
@Transactional
public class PaymentServicesImpl implements PaymentService{
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Map<String, String> uploadPayrolls(List<Payment> payments) throws Exception {
        for(var payment:payments){
            var paymentId = new PaymentId(ConversionUtils.stringToDate(payment.getPeriod()), payment.getEmployee());
            if(paymentRepository.existsById(paymentId)){
                throw new Exception("Payment already exists!");
            }
            var entity = ConversionUtils.dto2Entity(payment);
            paymentRepository.save(entity);
        }
        return Map.of("status", "Added successfully!");
    }

    @Override
    public Map<String, String> changeSalary(Payment payment) throws Exception {
        var paymentId = new PaymentId(ConversionUtils.stringToDate(payment.getPeriod()), payment.getEmployee());
        if(!paymentRepository.existsById(paymentId)){
            throw new Exception("Payment not found!");
        }
        var entity = ConversionUtils.dto2Entity(payment);
        entity.setPeriod(ConversionUtils.stringToDate(payment.getPeriod()));
        paymentRepository.save(entity);
        return Map.of("status", "Updated successfully!");
    }

    @Override
    public Object getEmployeePayByPeriod(String period, String email) throws Exception {
        LocalDate payPeriod = ConversionUtils.stringToDate(period);
        PaymentEntity paymentEntity = paymentRepository.findByPeriodAndEmployeeIgnoreCase(payPeriod, email);
        AccountEntity employee = accountRepo.findByEmailIgnoreCase(email);
        return paymentEntity != null ? ConversionUtils.entity2DTO(paymentEntity, employee) : Map.of();
    }

    public List<Payment> getEmployeePayForAllPeriodsDesc(String email) throws Exception{
        List<PaymentEntity> payments = paymentRepository.findAll();
        List<PaymentEntity> paymentEntities = paymentRepository.findByEmployeeIgnoreCaseOrderByPeriodDesc(email);
        AccountEntity employee = accountRepo.findByEmailIgnoreCase(email);
        return paymentEntities.stream()
                .map(paymentEntity -> ConversionUtils.entity2DTO(paymentEntity, employee))
                .collect(Collectors.toList());
    }
}
