package app.library.services.service;

import app.library.repository.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    void save(User user) throws Exception;

    User getById(Long id);

    User getByName(String name);

    void update(User user);

    void delete(Long id);
}