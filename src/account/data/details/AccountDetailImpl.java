package account.data.details;

import account.data.entities.AccountEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDetailImpl implements UserDetails {
    private final String username;
    private final String password;

    private final boolean isAccountNonLocked;
    private final List<GrantedAuthority> role;

    public AccountDetailImpl(AccountEntity accountEntity){
        this.isAccountNonLocked = accountEntity.getAccountNonLocked();
        this.username = accountEntity.getEmail();
        this.password = accountEntity.getPassword();
        this.role = accountEntity.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
