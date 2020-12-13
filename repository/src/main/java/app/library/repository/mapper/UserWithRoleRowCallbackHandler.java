package app.library.repository.mapper;

import app.library.repository.entity.Role;
import app.library.repository.entity.RoleType;
import app.library.repository.entity.User;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserWithRoleRowCallbackHandler implements RowCallbackHandler {
    private User user = null;
    private Set<Role> roles = null;

    public void processRow(ResultSet rs) {
        if (Objects.isNull(roles)) {
            roles = new HashSet<>();
        }
        try {
            long roleId = rs.getLong("c.id");
            Role role = new Role();
            role.setId(roleId);
            role.setRoleType(RoleType.valueOf(rs.getString("c.postingDate")));
            roles.add(role);
            long userId = rs.getLong("d.id");
            if (user == null || userId != user.getId()) {
                user = new User();
                user.setId(userId);
                user.setName(rs.getString("d.description"));
                user.setEmail(rs.getString("d.description"));
            }
            user.setRoles(roles);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }
}