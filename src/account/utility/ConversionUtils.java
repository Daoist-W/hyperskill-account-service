package account.utility;

import account.data.dtos.Payment;
import account.data.entities.AccountEntity;
import account.data.entities.PaymentEntity;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConversionUtils {
    private static ModelMapper modelMapper = new ModelMapper();

    public static LocalDate stringToDate(String value){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse("01-" + value, formatter);
    }

    public static PaymentEntity  dto2Entity(Payment paymentDTO){
        var entity = new PaymentEntity();
        entity.setEmployee(paymentDTO.getEmployee());
        entity.setPeriod(stringToDate(paymentDTO.getPeriod()));
        entity.setSalary(Long.parseLong(paymentDTO.getSalary()));
        return entity;
    }

    public static Payment entity2DTO(PaymentEntity paymentEntity, AccountEntity accountEntity){
        var dto = new Payment();
        dto.setEmployee(paymentEntity.getEmployee());
        dto.setName(accountEntity.getName());
        dto.setLastname(accountEntity.getLastname());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM-yyyy");
        dto.setPeriod(formatter.format(paymentEntity.getPeriod()));
        dto.setSalary(salaryConverter(paymentEntity.getSalary()));
        return dto;
    }

    private static String salaryConverter(Long salary){
        return String.format("%d dollar(s) %d cent(s)", salary / 100, salary % 100);
    }
}
