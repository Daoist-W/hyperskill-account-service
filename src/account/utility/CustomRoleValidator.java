package account.utility;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomRoleValidator implements ConstraintValidator<ValidCustomRoles, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println(value);
        return AppValidator.validateRole(value);
    }
}
