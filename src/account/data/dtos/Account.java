package account.data.dtos;

        import account.utility.ValidCustomDate;
        import account.utility.ValidCustomRoles;
        import com.fasterxml.jackson.annotation.JsonProperty;

        import javax.persistence.*;
        import javax.validation.constraints.NotEmpty;
        import javax.validation.constraints.Pattern;
        import javax.validation.constraints.Size;
        import java.util.List;

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 30)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 30)
    private String lastname;

    @NotEmpty
    @Pattern(regexp = "\\w+(@acme.com)$")
    @Size(min = 1, max = 30)
    private String email;

    @NotEmpty
    @JsonProperty(required = true, access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}




