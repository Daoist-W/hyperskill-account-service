package account.data.details;

import account.utility.OperationType;
import account.utility.ValidCustomRoles;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;

public class RoleAssignDetails {
    private String user;

    @ValidCustomRoles
    private String role;

    @JsonProperty("operation")
    private OperationType operationType;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
}
