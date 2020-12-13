package app.library.web.converters;

import app.library.web.dto.RoleDTO;
import app.library.web.dto.UserDTO;
import app.library.repository.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;
import java.util.stream.Collectors;

public class UserToUserDtoConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        Set<RoleDTO> roles = user.getRoles().stream()
                .map(role -> new RoleToRoleDtoConverter().convert(role))
                .collect(Collectors.toSet());
        userDTO.setRoles(roles);
        return userDTO;
    }
}