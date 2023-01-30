package account.exceptions;

public class MatchingPasswordsException extends RuntimeException{
    public MatchingPasswordsException(String message) {
        super(message);
    }
}
