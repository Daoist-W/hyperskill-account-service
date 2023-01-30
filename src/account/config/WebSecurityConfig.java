package account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationEntryPoint restAuthenticationPoint;

    @Autowired
    UserDetailsService accountService;

    @Autowired
    AccessDeniedHandler customAccessDeniedHandler;

//    @Autowired
//    BasicAuthenticationFilter customBasicAuthFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(accountService)
                .passwordEncoder(getEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO: implement me!
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationPoint) // handles auth errors
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler) // handle access privilege errors
                .and()
                .csrf().disable().headers().frameOptions().disable() // postman, h2 console
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth/signup", "/api/auth/signup/").permitAll()
                .antMatchers(HttpMethod.GET, "/api/security/events", "/api/security/events/").hasAnyRole("AUDITOR")
                .mvcMatchers("/api/empl/**").hasAnyRole("USER", "ACCOUNTANT")
                .mvcMatchers("/api/acct/**").hasAnyRole("ACCOUNTANT")
                .mvcMatchers("/api/admin/**").hasAnyRole("ADMINISTRATOR")
                .mvcMatchers("/api/**").authenticated() // all other endpoints authenticated
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
    }

    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder(13);
    }

    @Bean
    public AuthenticationEntryPoint getRestAuthenticationPoint(){
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public  AccessDeniedHandler getCustomAccessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

}
