package app.library.services.service;

import app.library.repository.dao.UserRepository;
import app.library.repository.entity.Role;
import app.library.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static app.library.repository.entity.RoleType.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepository userDao;

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void save(User user) {
        if (user.getName().length() < 2) {
            throw new IllegalArgumentException();
        }
        Set<Role> roles =  user.getRoles();
        if(roles.isEmpty()) {
            Role role = new Role();
            role.setRoleType(ROLE_USER);
        }
        userDao.save(user);
    }

    public User getByName(String name){
        return userDao.getByName(name);
    }

    public User getById(Long id) {
        return userDao.getById(id);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(Long id) {
        userDao.delete(id);
    }
}
