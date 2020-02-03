package com.znamenacek.jakub.spring_boot_security_book.controllers;

import com.znamenacek.jakub.spring_boot_security_book.models.User;
import com.znamenacek.jakub.spring_boot_security_book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUserById(@PathVariable Integer id){
        userService.deleteUserById(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Integer id, @RequestBody User user){
        return userService.updateUser(id,user)
                .map(user1 -> new ResponseEntity(user1, HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(null,HttpStatus.NOT_FOUND));
    }
}
