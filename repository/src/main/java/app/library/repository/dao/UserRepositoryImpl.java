package app.library.repository.dao;

import app.library.repository.entity.Role;
import app.library.repository.entity.User;
import app.library.repository.mapper.RoleMapper;
import app.library.repository.mapper.UserMapper;

import app.library.repository.mapper.UsersWithRolesRowCallbackHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void save(User user) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
//        int id = jdbcTemplate.update(sql, user.getName(), user.getEmail());
        Map<String, Object> ids = execute(sql, user);
        Long id = (Long) ids.get("id");
        if (Objects.nonNull(id)) {
            Set<Role> roles = user.getRoles();
            String sqlUserRole = "INSERT INTO user_role\n" +
                    "(user_id, role_id)\n" +
                    "VALUES\n" +
                    "(?, (SELECT roles.id as role_id FROM roles WHERE role_type = ?))";

            batchInsert(sqlUserRole, new ArrayList<>(roles), id);
        }

    }

    private Map<String, Object> execute(final String sql, User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getEmail());
                    return ps;
                },
                keyHolder);
        return keyHolder.getKeys();
    }

    public int[] batchInsert(String roleIdSql, List<Role> roles, Long userId) {
        return this.jdbcTemplate.batchUpdate(
                roleIdSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, userId);
                        ps.setString(2, roles.get(i).getRoleType().name());
                    }

                    public int getBatchSize() {
                        return roles.size();
                    }
                });
    }


//    public int[] batchInsert(int[] ints, String sqlUserRole, int userId) {
//        return this.jdbcTemplate.batchUpdate(
//                sqlUserRole,
//                new BatchPreparedStatementSetter() {
//                    public void setValues(PreparedStatement ps, int i) throws SQLException {
//                        ps.setLong(1, ints[i]);
//                    }
//
//                    public int getBatchSize() {
//                        return ints.length;
//                    }
//                });
//    }


    public User getById(Long id) {
        String userSql = "SELECT * FROM users WHERE id = ?";
        String rolesSql = "SELECT roles.id as role_id, roles.role_type as role_type FROM roles\n" +
                "         LEFT JOIN user_role ON roles.id = user_role.role_id\n" +
                "         LEFT JOIN users ON users.id = user_role.user_id WHERE users.id = ?";
        User user = jdbcTemplate.queryForObject(userSql, new UserMapper(), id);
        List<Role> roles = jdbcTemplate.query(rolesSql, new RoleMapper(), id);
        Optional.ofNullable(user)
                .ifPresent(elem -> user.setRoles(new HashSet<>(roles)));
        return user;
    }

    public User getByName(String name) {
        String sql = "SELECT * FROM users WHERE name = :name";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return namedParameterJdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(User.class));
    }

    public List<User> findAll() {
//        String sql = "SELECT * FROM users";
//        return jdbcTemplate.query(sql, new UserMapper());
        UsersWithRolesRowCallbackHandler rowCallbackHandler = new UsersWithRolesRowCallbackHandler();
        jdbcTemplate.query(
                "SELECT users.id as user_id, users.name as name, users.email as email,\n" +
                        "       r.id as role_id, r.role_type as role_type\n" +
                        "FROM users LEFT JOIN user_role ON users.id = user_role.user_id\n" +
                        "           LEFT JOIN roles r ON user_role.role_id = r.id\n" +
                        "ORDER BY user_id, role_id",
                rowCallbackHandler);
        return rowCallbackHandler.getUsers();
    }

    public void update(User user) {
        String userSql = "UPDATE users SET name=?, email=? WHERE id=?";
        jdbcTemplate.update(userSql, user.getName(), user.getEmail(), user.getId());

    }

    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
