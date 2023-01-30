package account.services;

import account.data.dtos.Account;
import account.data.entities.AccountEntity;
import account.data.details.AccountDetailImpl;
import account.exceptions.GenericBadRequestException;
import account.exceptions.MatchingPasswordsException;
import account.exceptions.UserExistsException;
import account.repo.AccountRepo;
import account.utility.AppValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    LoggingService loggingService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;

    /**
     * Registration method. If account repository is empty, the first user to register will be
     * assigned ROLE_ADMINISTRATOR.
     * Subsequent registrations will be assigned ROLE_USER upon registration
     * @param account
     * @return
     * @throws UserExistsException
     */
    public Account register(Account account) throws UserExistsException {
        PasswordValidationService.validate(account.getPassword());
        if (accountRepo.findByEmailIgnoreCase(account.getEmail()) != null)
            throw new UserExistsException("User exist!");
        else {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setEmail(account.getEmail().toLowerCase());
            var entity = modelMapper.map(account, AccountEntity.class);
            assignInitialRole(entity);
            Account accountDTO = modelMapper.map(accountRepo.save(entity), Account.class);
            loggingService.afterCreatingUser(accountDTO.getEmail());
            return accountDTO;
        }
    }

    private void assignInitialRole(AccountEntity entity) {
        if(accountRepo.count() == 0){
            entity.setRoles(List.of("ROLE_ADMINISTRATOR"));
        } else {
            entity.setRoles(List.of("ROLE_USER"));
        }
    }

    public Map<String, String> updatePass(String username, String pass){
        PasswordValidationService.validate(pass);
        var account = getAccountByUsername(username);
        if (passwordEncoder.matches(pass, account.getPassword()))
            throw new MatchingPasswordsException("The passwords must be different!");
        account.setPassword(passwordEncoder.encode(pass));
        accountRepo.save(modelMapper.map(account, AccountEntity.class));
        loggingService.afterUpdatePassword(username);
        return Map.of(
                "email", account.getEmail(),
                "status", "The password has been updated successfully");
    }

    public Account getAccountByUsername(String username) throws UsernameNotFoundException{
        var account = accountRepo.findByEmailIgnoreCase(username);
        if (account == null) throw new UsernameNotFoundException("Not found: " + username);
        return modelMapper.map(account, Account.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountRepo.findByEmailIgnoreCase(username);
        if (account == null) throw new UsernameNotFoundException("Not found: " + username);
        return new AccountDetailImpl(account);
    }

    public boolean resetUserAttempts(String username){
        var account = accountRepo.findByEmailIgnoreCase(username);
        if (account == null) throw new UsernameNotFoundException("Not found: " + username);
        if (account.getFailedAttempts() == 0) return false;
        account.setFailedAttempts(0);
        return true;
    }

    public boolean addFailedAttemptReturnIfNonLocked(String username) throws GenericBadRequestException {
        var account = accountRepo.findByEmailIgnoreCase(username);
        Integer noOfFailedAttempts = 0;
        boolean isNotNull = account != null;
        if (isNotNull){
            noOfFailedAttempts = account.getFailedAttempts();
            account.setFailedAttempts(++noOfFailedAttempts);
            if(noOfFailedAttempts >= 5) {
                lockUser(username);
            }
        }
        loggingService.afterAddFailedAttempt(noOfFailedAttempts);
        return account != null ? account.getAccountNonLocked() : true;
    }

    public boolean lockUser(String username) throws  GenericBadRequestException{
        var account = accountRepo.findByEmailIgnoreCase(username);
        if (account == null) throw new UsernameNotFoundException("Not found: " + username);
        if (AppValidator.validateAdminRole(account.getRoles())) throw new GenericBadRequestException("Can't lock the ADMINISTRATOR!");
        account.setAccountNonLocked(false);
        System.out.println("user: " + username + ", ifNonLocked: " + account.getAccountNonLocked());
        return true;
    }

    public boolean unlockUser(String username){
        var account = accountRepo.findByEmailIgnoreCase(username);
        if (account == null) throw new UsernameNotFoundException("Not found: " + username);
        account.setAccountNonLocked(true);
        account.setFailedAttempts(0);
        System.out.println("user: " + username + ", ifNonLocked: " + account.getAccountNonLocked());
        return true;
    }
}
