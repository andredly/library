package app.library.web.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserDTO {

    private String name;
    private String email;

    private Set<RoleDTO> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(name, userDTO.name) && Objects.equals(email, userDTO.email) && Objects.equals(roles, userDTO.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, roles);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }
}
