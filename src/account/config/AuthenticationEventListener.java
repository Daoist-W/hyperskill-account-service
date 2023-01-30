package account.config;

import account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener implements ApplicationListener<AuthenticationSuccessEvent>{

    @Autowired
    private AccountService accountService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        accountService.resetUserAttempts(username);
    }
}
