package app.library.web.converters;

import app.library.web.dto.RoleDTO;
import app.library.web.dto.UserDTO;
import app.library.repository.entity.Role;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleToRoleDtoConverter implements Converter<Role, RoleDTO> {

    @Override
    public RoleDTO convert(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleType(role.getRoleType());
        Set<UserDTO> users = role.getUsers().stream()
                .map(user -> new UserToUserDtoConverter().convert(user))
                .collect(Collectors.toSet());
        roleDTO.setUsers(users);
        return roleDTO;
    }
}