package app.library.repository.mapper;

import app.library.repository.entity.Role;
import app.library.repository.entity.RoleType;
import app.library.repository.entity.User;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UsersWithRolesRowCallbackHandler implements RowCallbackHandler {
    private List<User> users;
    private User user = null;
    private Set<Role> roles = null;

    public void processRow(ResultSet rs) {
        if (Objects.isNull(users)) {
            users = new ArrayList<>();
        }

        try {
            long userId = rs.getLong("user_id");
            if (user == null || userId != user.getId()) {
                user = new User();
                user.setId(userId);
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                roles = new HashSet<>();
                user.setRoles(roles);
                users.add(user);
            }
            long roleId = rs.getLong("role_id");
            if (userId == user.getId()) {
                Role role = new Role();
                role.setId(roleId);
                role.setRoleType(RoleType.valueOf(rs.getString("role_type")));
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    public List<User> getUsers() {
        return users;
    }
}