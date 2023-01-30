package account.data.details;

import account.utility.AccessOperation;

import javax.validation.constraints.NotEmpty;

public class ModifyAccessDetails {
    @NotEmpty
    private String user;
    private AccessOperation operation;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public AccessOperation getOperation() {
        return operation;
    }

    public void setOperation(AccessOperation operation) {
        this.operation = operation;
    }
}
