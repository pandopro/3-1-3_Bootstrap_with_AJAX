package com.stecenko.demo.service;
import com.stecenko.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User findUser = userService.findByLogin(userName);
        if (findUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + userName);
        }
        return findUser;
    }
}