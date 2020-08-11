package com.stecenko.demo.service;

import com.stecenko.demo.model.User;

public interface UserService {
    User findByLogin(String login);

    boolean demo();

    <S extends User> S save(S s);

    User findById(Long id);

    Iterable<User> findAll();

    void deleteById(Long id);

    void edit(User user);
}
