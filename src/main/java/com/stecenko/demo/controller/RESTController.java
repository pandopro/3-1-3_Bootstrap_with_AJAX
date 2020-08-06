package com.stecenko.demo.controller;

import com.google.gson.Gson;
import com.stecenko.demo.model.Role;
import com.stecenko.demo.model.User;
import com.stecenko.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/rest")
public class RESTController {
    @Autowired
    public UserService userService;

    @DeleteMapping("/delete/{id}")
    public String Delete(@PathVariable(value = "id") String idForDel) {
        System.out.println("------------------------Delete------ping " + idForDel);
        userService.deleteById(Long.parseLong(idForDel));
        return idForDel;
    }

    @GetMapping("/all")
    public String getUsers() {
        Gson gson = new Gson();
        String json = gson.toJson(userService.findAll());
        return json;
    }

    @PutMapping("/edit")
    public Boolean edit(String user, String roles) {
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
        return true;
    }

    @PostMapping("/get")
    public String getUser(@RequestBody String id) {
        Gson gson = new Gson();
        String json = gson.toJson(userService.findById(Long.parseLong(id)));
        return json;
    }

    @PostMapping("/add")
    public String addUser(String user, String roles) {
        System.out.println("------------------------------add " + user + " ||| " + roles);
        Gson gson = new Gson();
        User newUser = gson.fromJson(user, User.class);
        String[] newRoles = gson.fromJson(roles, String[].class);
        Set<Role> setRoles = new HashSet<>();
        for (String s : newRoles[0].split(",")) {
            setRoles.add(new Role(s));
        }
        newUser.setRoles(setRoles);
        userService.save(newUser);
        System.out.println("Итог  " + newUser.toString());
        return newUser.getId().toString();
    }
}