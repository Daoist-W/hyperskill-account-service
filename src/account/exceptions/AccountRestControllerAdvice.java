package account.exceptions;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AccountRestControllerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> constraintExceptionHandler(Exception exception, WebRequest request) {
        var errorInfo = new ErrorInfo();
        errorInfo.setTimestamp(LocalDate.now());
        if (exception instanceof MethodArgumentNotValidException) {
            var ex = (MethodArgumentNotValidException) exception;
             if (ex.getLocalizedMessage() != null) {
                 if(ex.getLocalizedMessage().contains("Role not found!")){
                     errorInfo.setMessage("Role not found!");
                     errorInfo.setPath(request.getDescription(false).substring(4));
                     errorInfo.setStatus(HttpStatus.NOT_FOUND.value());
                     errorInfo.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
                     return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
                 } else {
                     errorInfo.setMessage(Arrays.stream(ex.getLocalizedMessage()
                                     .split(","))
                             .map(m -> m.replaceAll("^ ?[A-Za-z0-9]*\\.", ""))
                             .collect(Collectors.joining(", "))
                     );
                 }
            }
        } else {
            var ex = (ConstraintViolationException) exception;
            if (ex.getLocalizedMessage() != null) {
                errorInfo.setMessage(Arrays.stream(ex.getLocalizedMessage()
                                .split(","))
                        .map(m -> m.replaceAll("^ ?[A-Za-z0-9]*\\.", ""))
                        .collect(Collectors.joining(", "))
                );
            }
        }

        errorInfo.setPath(request.getDescription(false).substring(4));
        errorInfo.setStatus(HttpStatus.BAD_REQUEST.value());
        errorInfo.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorInfo> genericBadExceptionHandler(Exception exception, WebRequest request) {
        var errorInfo = new ErrorInfo();
        errorInfo.setTimestamp(LocalDate.now());
        errorInfo.setMessage(exception.getMessage());
        errorInfo.setPath(request.getDescription(false).substring(4));
        errorInfo.setStatus(HttpStatus.BAD_REQUEST.value());
        errorInfo.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ErrorInfo> userNotFoundExceptionHandler(UsernameNotFoundException exception, WebRequest request) {
        var errorInfo = new ErrorInfo();
        errorInfo.setTimestamp(LocalDate.now());
        errorInfo.setMessage(exception.getMessage());
        errorInfo.setPath(request.getDescription(false).substring(4));
        errorInfo.setStatus(HttpStatus.NOT_FOUND.value());
        errorInfo.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);

    }

}
