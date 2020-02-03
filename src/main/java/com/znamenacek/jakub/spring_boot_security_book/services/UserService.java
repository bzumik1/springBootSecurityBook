package com.znamenacek.jakub.spring_boot_security_book.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.znamenacek.jakub.spring_boot_security_book.models.User;
import com.znamenacek.jakub.spring_boot_security_book.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Integer id){
        return userRepository.findById(id);
    }

    public void deleteUserById(Integer id){
        var userInDB = findUserById(id);
        userInDB.ifPresent(user -> userRepository.delete(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Optional<User> updateUser(Integer id, User user){
        var userInDB = findUserById(id);
        if(userInDB.isPresent()){
            user.setId(userInDB.get().getId());
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

}
