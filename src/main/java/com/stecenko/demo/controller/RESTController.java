package com.stecenko.demo.controller;

import com.google.gson.Gson;
import com.stecenko.demo.model.Role;
import com.stecenko.demo.model.User;
import com.stecenko.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/rest")
public class RESTController {
    @Autowired
    public UserService userService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> Delete(@RequestBody long idForDel) {
        //   System.out.println("------------------------Delete------ping " + idForDel);
        userService.deleteById(idForDel);

        return new ResponseEntity(idForDel, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), new HttpHeaders(), HttpStatus.OK);

    }

    @PutMapping(value = "/edit")
    public ResponseEntity<String> edit(String user, String roles) {
        Gson gson = new Gson();
        User newUser = gson.fromJson(user, User.class);
        String[] newRoles = gson.fromJson(roles, String[].class);
        if ("[]".equals(roles)) {
            newUser.setRoles(
                    userService.findById(
                            newUser.getId()
                    ).getRoles());
        } else {
            Set<Role> setRoles = new HashSet<>();
            for (String s : newRoles[0].split(",")) {
                setRoles.add(new Role(s));
            }
            newUser.setRoles(setRoles);
        }

        userService.edit(newUser.getId(), newUser);
        return new ResponseEntity<>("true", new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/get")
    public ResponseEntity<User> getUser(@RequestBody long id) {
        return new ResponseEntity(userService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(String user, String roles) {
        Gson gson = new Gson();
        User newUser = gson.fromJson(user, User.class);
        String[] newRoles = gson.fromJson(roles, String[].class);
        Set<Role> setRoles = new HashSet<>();
        for (String s : newRoles[0].split(",")) {
            setRoles.add(new Role(s));
        }
        newUser.setRoles(setRoles);
        userService.save(newUser);
        return new ResponseEntity<>(newUser.getId().toString(), new HttpHeaders(), HttpStatus.OK);
    }
}