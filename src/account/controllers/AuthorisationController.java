package account.controllers;

import account.data.dtos.Account;
import account.data.entities.AccountEntity;
import account.exceptions.GenericBadRequestException;
import account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class AuthorisationController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Account> register(@Valid @RequestBody Account account, Errors errors) {
        return ResponseEntity.ok(accountService.register(account));
    }

    @PostMapping("api/auth/changepass")
    public ResponseEntity<Map<String, String>> changePass(@AuthenticationPrincipal UserDetails details, @RequestBody Map<String, String> pass){
        var responseBody = accountService.updatePass(details.getUsername().toLowerCase() ,pass.get("new_password"));
        return ResponseEntity.ok(responseBody);
    }

}