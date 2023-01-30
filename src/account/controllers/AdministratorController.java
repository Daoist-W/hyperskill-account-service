package account.controllers;

import account.data.details.ModifyAccessDetails;
import account.data.dtos.Account;
import account.data.details.RoleAssignDetails;
import account.exceptions.GenericBadRequestException;
import account.services.AdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/admin")
public class AdministratorController {

    @Autowired
    private AdministrationService adminService;

    @PutMapping("/user/role")
    public ResponseEntity<Account> setUserRole(
            @Valid
            @RequestBody
            RoleAssignDetails roleAssignDetails) throws GenericBadRequestException {
        return new ResponseEntity<>(adminService.setAccountRole(roleAssignDetails), HttpStatus.OK);
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable String email) throws GenericBadRequestException {
        adminService.deleteAccountByEmail(email.toLowerCase());
        return ResponseEntity.ok(Map.of("user", email.toLowerCase(), "status", "Deleted successfully!"));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Account>> getAllUsers(){
        return new ResponseEntity<>(adminService.getAllAccounts(), HttpStatus.OK);
    }

    @PutMapping("/user/access")
    public ResponseEntity<Map<String, String>> updateUserAccess(@RequestBody ModifyAccessDetails modifyAccessDetails) throws GenericBadRequestException {
        String message = adminService.updateUserAccess(modifyAccessDetails);
        return ResponseEntity.ok(Map.of("status", message));
    }


}
