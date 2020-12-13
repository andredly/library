package app.library.repository.mapper;

import app.library.repository.entity.Role;
import app.library.repository.entity.RoleType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {

    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong("role_id"));
        role.setRoleType(RoleType.valueOf(resultSet.getString("role_type")));
        return role;
    }
}
