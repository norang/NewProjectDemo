package com.example.demo.service;

import java.util.List;

import com.example.demo.model.User;



public interface UserService {
    void save(User user);

    User findByUsername(String username);
    
    List<User> findAll();
    
}
