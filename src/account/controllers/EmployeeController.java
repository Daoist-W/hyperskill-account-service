package account.controllers;

import account.data.dtos.Account;
import account.data.dtos.Payment;
import account.services.AccountService;
import account.services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("api/empl/payment")
    public ResponseEntity<List<Payment>> getDetails(@AuthenticationPrincipal UserDetails details) throws Exception {
        List<Payment> payments = paymentService.getEmployeePayForAllPeriodsDesc(details.getUsername());
        return ResponseEntity.ok(payments);
    }

    @GetMapping(value = "api/empl/payment", params = "period")
    public ResponseEntity<Object> getEmployeePayByPeriod(
            @RequestParam("period") String period,
            @AuthenticationPrincipal UserDetails details) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        var response = paymentService.getEmployeePayByPeriod(period, details.getUsername());
        return response == null ? new ResponseEntity<Object>(headers, HttpStatus.OK) : ResponseEntity.ok(response);
    }

}








