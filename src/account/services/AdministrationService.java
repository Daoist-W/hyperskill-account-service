package account.services;

import account.data.details.ModifyAccessDetails;
import account.data.details.RoleAssignDetails;
import account.data.dtos.Account;
import account.exceptions.GenericBadRequestException;

import java.util.List;

public interface AdministrationService {
    public Account setAccountRole(RoleAssignDetails roleAssignDetails) throws GenericBadRequestException;

    public void deleteAccountByEmail(String email) throws GenericBadRequestException;

    public List<Account> getAllAccounts();

    String updateUserAccess(ModifyAccessDetails modifyAccessDetails) throws GenericBadRequestException;
}
