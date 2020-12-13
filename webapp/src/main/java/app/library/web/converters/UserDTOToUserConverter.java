package app.library.web.converters;

import app.library.web.dto.UserDTO;
import app.library.repository.entity.Role;
import app.library.repository.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDTOToUserConverter implements Converter<UserDTO, User> {

    @Override
    public User convert(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        Set<Role> roles = userDTO.getRoles().stream()
                .map(role -> new RoleDTOToRoleConverter().convert(role))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }
}