package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Role;




public interface RoleService {
	Role findById(long id);
    List<Role> findAll();
    
}
