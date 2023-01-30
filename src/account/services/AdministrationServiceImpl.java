package account.services;

import account.data.details.ModifyAccessDetails;
import account.data.details.RoleAssignDetails;
import account.data.dtos.Account;
import account.data.entities.AccountEntity;
import account.exceptions.GenericBadRequestException;
import account.repo.AccountRepo;
import account.utility.AccessOperation;
import account.utility.AppValidator;
import account.utility.OperationType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service("administrationService")
public class AdministrationServiceImpl implements AdministrationService{

    public static final String ADMINISTRATOR = "ROLE_ADMINISTRATOR";
    public static final String ACCOUNTANT = "ROLE_ACCOUNTANT";

    public static final String USER = "ROLE_USER";
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoggingService loggingService;

    private ModelMapper mapper =  new ModelMapper();

    @Override
    public Account setAccountRole(RoleAssignDetails roleAssignDetails) throws GenericBadRequestException {
        AccountEntity accountEntity = accountRepo.findByEmailIgnoreCase(roleAssignDetails.getUser());
        if (accountEntity == null) throw new UsernameNotFoundException("User not found!");

        // grant or remove role
        OperationType operationType = roleAssignDetails.getOperationType();
        String candidateRole = "ROLE_" + roleAssignDetails.getRole();
        List<String> existingRoles = accountEntity.getRoles();
        if (operationType == OperationType.GRANT){
            //validateGrantRole
            if(AppValidator.ifRolesConflict(existingRoles, candidateRole)){
                throw new GenericBadRequestException("The user cannot combine administrative and business roles!");
            }
            // check role not already present
            if(!existingRoles.contains(candidateRole)){
                accountEntity.getRoles().add(0, candidateRole);
            }
        } else if (roleAssignDetails.getOperationType() == OperationType.REMOVE){
            // validateRemoveRole
            if (candidateRole.equals(ADMINISTRATOR)){
                throw new GenericBadRequestException("Can't remove ADMINISTRATOR role!");
            }
            if(!existingRoles.contains(candidateRole)){
                throw new GenericBadRequestException("The user does not have a role!");
            }
            if(existingRoles.size() == 1){
                throw new GenericBadRequestException("The user must have at least one role!");
            }
            accountEntity.getRoles().remove(candidateRole);
        }
        loggingService.afterUpdatingUserRoles(roleAssignDetails);
        return mapper.map(accountEntity, Account.class);
    }

    @Override
    public void deleteAccountByEmail(String email) throws GenericBadRequestException {
        if (!accountRepo.existsRoleByEmailIgnoreCase(email))
            throw new UsernameNotFoundException("User not found!");
        AccountEntity accountEntity = accountRepo.findByEmailIgnoreCase(email);
        if(accountEntity.getRoles().contains("ROLE_ADMINISTRATOR")){
            throw new GenericBadRequestException("Can't remove ADMINISTRATOR role!");
        }
        accountRepo.deleteById(accountEntity.getId());
        loggingService.afterDeletingUser(email.toLowerCase());
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAll().stream()
                .map(accountEntity -> mapper.map(accountEntity, Account.class))
                .collect(Collectors.toList());
    }

    @Override
    public String updateUserAccess(ModifyAccessDetails modifyAccessDetails) throws GenericBadRequestException {
        String username = modifyAccessDetails.getUser().toLowerCase();
        AccessOperation operation = modifyAccessDetails.getOperation();
        String action = "";

        if(operation.equals(AccessOperation.LOCK)){
            accountService.lockUser(username);
            action = "locked";
        } else if (operation.equals(AccessOperation.UNLOCK)) {
            accountService.unlockUser(username);
            action = "unlocked";
        }
        loggingService.afterUpdatingUserAccess(modifyAccessDetails);
        return String.format("User %s %s!", username, action);
    }
}
