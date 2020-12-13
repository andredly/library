package app.library.web.converters;

import app.library.web.dto.RoleDTO;
import app.library.repository.entity.Role;
import app.library.repository.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleDTOToRoleConverter implements Converter<RoleDTO, Role> {

    @Override
    public Role convert(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleType(roleDTO.getRoleType());
        Set<User> users = roleDTO.getUsers().stream()
                .map(user -> new UserDTOToUserConverter().convert(user))
                .collect(Collectors.toSet());
        role.setUsers(users);
        return role;
    }
}