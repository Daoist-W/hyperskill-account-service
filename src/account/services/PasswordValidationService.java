package account.services;

import account.data.entities.BreachedPasswords;
import account.exceptions.BreachedPasswordException;
import account.exceptions.InvalidPasswordException;

public class PasswordValidationService {
    public static boolean validate(String password){
        if (!password.matches("^(?=.{12,})[A-Za-z\\d@$!%*#?&]*$")) throw new InvalidPasswordException("Password length must be 12 chars minimum!");
        if (BreachedPasswords.contains(password)) throw new BreachedPasswordException("The password is in the hacker's database!");
        return true;
    }
}
