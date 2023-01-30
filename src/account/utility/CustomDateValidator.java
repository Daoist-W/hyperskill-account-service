package account.utility;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class CustomDateValidator implements ConstraintValidator<ValidCustomDate, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse("01-" + value, formatter);
            return true;
        } catch (DateTimeParseException e){
            e.printStackTrace();
        }
        return false;
    }
}
