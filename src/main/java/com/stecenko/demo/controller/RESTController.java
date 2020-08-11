package com.stecenko.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stecenko.demo.model.User;
import com.stecenko.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class RESTController {
    @Autowired
    public UserService userService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> Delete(@RequestBody long idForDel) {
        userService.deleteById(idForDel);
        return new ResponseEntity(idForDel, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<String> edit(@RequestBody User user) throws JsonProcessingException {
        if ("".equals(user.getStringRoles())) {
            user.setRoles(userService.findById(user.getId()).getRoles());
        }
        userService.edit(user);
        return new ResponseEntity<>("true", new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/get")
    public ResponseEntity<User> getUser(@RequestBody long id) {
        return new ResponseEntity(userService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(user.getId().toString(), new HttpHeaders(), HttpStatus.OK);
    }
}
