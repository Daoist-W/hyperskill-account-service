package account.data.dtos;

import account.utility.ValidCustomDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@JsonSerialize
@JsonPropertyOrder({"name", "lastname","period","salary"})
public class Payment {

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String employee;

    @Id
    @ValidCustomDate
    private String period;

    @NotNull(message = "Employee's salary must be written!")
    @Min(value = 0, message = "Employee's salary must be non-negative!")
    private String salary;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastname;

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
