package account.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AppValidator {
    private static final HashSet<String> allRoles = new HashSet<>(List.of(
            "USER",
            "ACCOUNTANT",
            "ADMINISTRATOR",
            "AUDITOR"
    ));

    private static final HashSet<String> businessGroup = new HashSet<>(List.of(
            "ROLE_USER",
            "ROLE_ACCOUNTANT",
            "ROLE_AUDITOR"
    ));

    private static final HashSet<String> adminGroup = new HashSet<>(List.of(
            "ROLE_ADMINISTRATOR"
    ));

    public static boolean validateRole(String role){
        return allRoles.contains(role);
    }

    public static boolean validateBusinessRole(List<String> roles){
        for (String role: roles) {
            if(businessGroup.contains(role)){
                return true;
            }
        }
        return false;
    }

    public static boolean validateAdminRole(List<String> roles){
        for (String role: roles) {
            if(adminGroup.contains(role)){
                return true;
            }
        }
        return false;
    }

    public static boolean ifRolesConflict(List<String> existingRoles, String candidateRole) {
        List<String> roles = new ArrayList<>(existingRoles);
        roles.add(candidateRole);
        return validateAdminRole(roles) && validateBusinessRole(roles);
    }
}
