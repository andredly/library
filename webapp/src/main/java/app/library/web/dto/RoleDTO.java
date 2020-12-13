package app.library.web.dto;

import app.library.repository.entity.RoleType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RoleDTO {

    private RoleType roleType;

    private Set<UserDTO> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return roleType == roleDTO.roleType && Objects.equals(users, roleDTO.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleType, users);
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }
}