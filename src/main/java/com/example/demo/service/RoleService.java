package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Role;




public interface RoleService {
	Role findById(long id);
    List<Role> findAll();
    void save(Role role);
    
}
