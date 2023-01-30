package account.data.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BreachedPasswords {
    private static final Set<String> breachedPasses = new HashSet<>(List.of(
            "PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"
    ));

    public static boolean contains(String pass){
        return breachedPasses.contains(pass);
    }
}
