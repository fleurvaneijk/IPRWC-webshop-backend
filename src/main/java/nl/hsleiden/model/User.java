package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import java.security.Principal;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Fleur van Eijk
 */
public class User implements Principal
{
    @NotEmpty
    @Email
    @JsonProperty
    @JsonView(View.Public.class)
    private String email;

    @NotEmpty
    @JsonProperty
    @Length(min = 3, max = 100)
    @JsonView(View.Public.class)
    private String name;

    @NotEmpty
    @JsonProperty
    @Length(min = 8)
    @JsonView(View.Public.class)
    private String password;

    @JsonProperty
    @JsonView(View.Public.class)
    private String role;

    public User() {}

    public User(String email, String name, String password, String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getRole()
    {
        return role;
    }
    
    public boolean hasRole(String roleName)
    {
        if (role != null)
        {
            if(roleName.equals(role))
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean equals(User user)
    {
        return email.equals(user.getEmail());
    }
}
