package app.library.repository.dao;

import app.library.repository.entity.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    User getById(Long id);

    User getByName(String name);

    List<User> findAll();

    void update(User user);

    void delete(Long id);

}
