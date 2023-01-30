package account.services;

import account.data.details.ModifyAccessDetails;
import account.data.details.RoleAssignDetails;
import account.data.dtos.SecurityEvent;
import account.utility.AccessOperation;
import account.utility.ActionType;
import account.utility.OperationType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Base64;

@Service
public class LoggingService {
    private Logger LOGGER = LoggerFactory.getLogger("security.event.logger");

    @Autowired
    private SecurityService securityService;

    @Autowired
    private HttpServletRequest request;

    public String getUsernameFromHeader() {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null){
            byte[] decodedAuthHeaderBytes = Base64.getDecoder().decode(authorizationHeader.replace("Basic ", ""));
            String decodedAuthUsername = new String(decodedAuthHeaderBytes).split(":")[0];
            LOGGER.info(decodedAuthUsername.toLowerCase());
            return decodedAuthUsername.toLowerCase();
        }
        LOGGER.info("Anonymous");
        return "Anonymous";
    }

    private void registerSecurityEvent(ActionType actionType, Object object) {
        SecurityEvent securityEvent = new SecurityEvent();
        securityEvent.setDate(LocalDate.now());
        securityEvent.setAction(actionType);
        securityEvent.setSubject(getUsernameFromHeader());
        securityEvent.setObject((String) object);
        securityEvent.setPath(request.getRequestURI());
        securityService.registerEvent(securityEvent);
        LOGGER.info(actionType.toString());


    }


    public void afterCreatingUser(String targetUsername){
        registerSecurityEvent(ActionType.CREATE_USER, targetUsername.toLowerCase());
    }


    public void afterDeletingUser(String email){
        registerSecurityEvent(ActionType.DELETE_USER, email);
    }


    public void afterUpdatingUserAccess(ModifyAccessDetails modifyAccessDetails){
        String username = modifyAccessDetails.getUser().toLowerCase();
        String object = "";
        if(modifyAccessDetails.getOperation().equals(AccessOperation.LOCK)){
            object = String.format("Lock user %s", username);
            registerSecurityEvent(ActionType.LOCK_USER, object);
        } else if(modifyAccessDetails.getOperation().equals(AccessOperation.UNLOCK))  {
            object = String.format("Unlock user %s", username);
            registerSecurityEvent(ActionType.UNLOCK_USER, object);
        }

    }


    public void afterUpdatingUserRoles(RoleAssignDetails roleAssignDetails) {
        OperationType operation = roleAssignDetails.getOperationType();
        String role = roleAssignDetails.getRole();
        String user = roleAssignDetails.getUser().toLowerCase();
        String object = "";
        if(operation.equals(OperationType.GRANT)){
            object = String.format("Grant role %s to %s", role , user);
            registerSecurityEvent(ActionType.GRANT_ROLE, object);
        } else if(operation.equals(OperationType.REMOVE))  {
            object = String.format("Remove role %s from %s", role , user);
            registerSecurityEvent(ActionType.REMOVE_ROLE, object);
        }
    }


    public void afterAddFailedAttempt(Integer noOfAttempts){
        if(noOfAttempts < 5){
            registerSecurityEvent(ActionType.LOGIN_FAILED, request.getRequestURI());
        }
        else if (noOfAttempts == 5) {
            registerSecurityEvent(ActionType.LOGIN_FAILED, request.getRequestURI());
            registerSecurityEvent(ActionType.BRUTE_FORCE, request.getRequestURI());
            String object = String.format("Lock user %s", getUsernameFromHeader());
            registerSecurityEvent(ActionType.LOCK_USER, object);
        }
    }



    public void afterHandlingAccessDeniedEvent(){
        registerSecurityEvent(ActionType.ACCESS_DENIED, request.getRequestURI());
    }

    public void afterUpdatePassword(String username) {
        registerSecurityEvent(ActionType.CHANGE_PASSWORD, username.toLowerCase());
    }
}
