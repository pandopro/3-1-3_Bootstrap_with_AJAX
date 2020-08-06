package com.stecenko.demo.service;
import com.stecenko.demo.dao.UserDAO;
import com.stecenko.demo.model.Role;
import com.stecenko.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.HashSet;

@Service
@Transactional
public class UserServiceImp implements UserService {
    @Autowired
    private UserDAO userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public boolean demo() {
        HashSet<Role> roles1 = new HashSet<Role>();
        roles1.add(new Role("ADMIN"));
        roles1.add(new Role("USER"));
        userDao.save(new User("ADMIN", "SERVER", "ADMIN", "ADMIN", Collections.singleton(new Role("ADMIN"))));
        userDao.save(new User("Alex", "Detroit", "ihbb", "123", Collections.singleton(new Role("USER"))));
        userDao.save(new User("Anton", "Chelyabinsk", "qwerty@qwerty.ru", "123", roles1));
        userDao.save(new User("Igor", "Omsk", "igorOmsk@qwerty.ru", "123", Collections.singleton(new Role("USER"))));
        userDao.save(new User("Leska", "Murmansk", "leshka@qwerty.ru", "123", Collections.singleton(new Role("ADMIN"))));
        return true;
    }

    @Override
    public <S extends User> S save(S s) {
        s.setPassword(passwordEncoder.encode(s.getPassword()));
        return userDao.save(s);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).get();
    }

    @Override
    public Iterable<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public void edit(Long id, User user) {
        if (user.getRoles() == null) {
            user.setRoles(userDao.findById(id).get().getRoles());
        }
        if (user.getPassword().startsWith("$2a$10$") || user.getPassword().equals("")) {
            user.setPassword(userDao.findById(id).get().getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.save(user);
    }
}