package app.library.services.service;

import app.library.repository.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    List<User> findAll();

    @Transactional
    void save(User user) throws Exception;

    User getById(Long id);

    User getByName(String name);

    @Transactional
    void update(User user);

    @Transactional
    void delete(Long id);
}