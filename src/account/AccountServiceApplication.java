package account;

import account.data.dtos.Payment;
import account.data.entities.PaymentEntity;
import account.utility.ConversionUtils;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(AccountServiceApplication.class, args);
        System.out.println("####################################");
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        System.out.println("####################################");
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}