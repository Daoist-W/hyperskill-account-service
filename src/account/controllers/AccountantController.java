package account.controllers;

import account.data.dtos.Payment;
import account.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Validated
@RestController("/api/acct")
public class AccountantController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("api/acct/payments")
    public ResponseEntity<Map<String, String>> uploadPayrolls(@RequestBody @Valid List<Payment> payments) throws Exception {
        return ResponseEntity.ok(paymentService.uploadPayrolls(payments));
    }

    @PutMapping("api/acct/payments")
    public ResponseEntity<Map<String, String>> changeSalary(@RequestBody @Valid Payment payment) throws Exception {
        return ResponseEntity.ok(paymentService.changeSalary(payment));
    }
}
