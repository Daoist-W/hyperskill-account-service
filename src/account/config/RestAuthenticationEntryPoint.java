package account.config;

import account.exceptions.GenericBadRequestException;
import account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private AccountService accountService;

    public String getUsernameFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null){
            byte[] decodedAuthHeaderBytes = Base64.getDecoder().decode(authorizationHeader.replace("Basic ", ""));
            String decodedAuthUsername = new String(decodedAuthHeaderBytes).split(":")[0];
            return decodedAuthUsername;
        }
        return "Anonymous";
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String username = getUsernameFromHeader(request);
        if(!username.equals("Anonymous")) {
            boolean isNonLocked = false;
            try {
                isNonLocked = accountService.addFailedAttemptReturnIfNonLocked(username);
            } catch (GenericBadRequestException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
            if(!isNonLocked){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User account is locked");
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
