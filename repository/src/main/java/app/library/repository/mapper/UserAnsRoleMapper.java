package app.library.repository.mapper;

import app.library.repository.entity.UserAndRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAnsRoleMapper implements RowMapper<UserAndRole> {

    public UserAndRole mapRow(ResultSet resultSet, int i) throws SQLException {
        UserAndRole userAndRole = new UserAndRole();
        userAndRole.setRoleId(resultSet.getLong("role_id"));
        userAndRole.setUserId(resultSet.getLong("user_id"));
        return userAndRole;
    }
}
