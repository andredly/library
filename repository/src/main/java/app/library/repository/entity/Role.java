package app.library.repository.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Role {

    private Long id;
    private RoleType roleType;

    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && roleType == role.roleType && Objects.equals(users, role.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleType, users);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}