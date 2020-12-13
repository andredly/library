package app.library.web.controllers;

import app.library.web.dto.UserDTO;
import app.library.repository.entity.User;
import app.library.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public ConversionService conversionService;

    @PostMapping(value = "/create",produces = "application/json")
    public ResponseEntity<UserDTO> createAccount(
            @RequestBody UserDTO userDTO) throws Exception {
        userService.save(conversionService.convert(userDTO, User.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        User user = userService.getById(id);


        return new ResponseEntity<>(conversionService.convert(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<UserDTO> getByName(@PathVariable String name) {
        User user = userService.getByName(name);
        return new ResponseEntity<>(conversionService.convert(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = userService.findAll();
        List<UserDTO> userDTOList = users.stream()
                .map(user -> conversionService.convert(user, UserDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
