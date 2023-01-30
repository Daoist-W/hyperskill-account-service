package account.controllers;

import account.data.dtos.SecurityEvent;
import account.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/security")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("/events")
    public ResponseEntity<List<SecurityEvent>> getAllSecurityEvents(){
        return ResponseEntity.ok(securityService.getAllSecurityEvents());
    }

}
