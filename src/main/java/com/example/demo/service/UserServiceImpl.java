package com.example.demo.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findById(Long.parseLong(user.getTitle())));
        user.setRoles(roles);
        
        
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public void addFailedLoginAttempt(String username) {
		User user = findByUsername(username);
		
		if (user!=null) {
			
			//new Fail Attempt 
			int oldFailAttempt = user.getFailedLoginAttempt();
			int newFailAttempt = oldFailAttempt <3 ? oldFailAttempt+1 : oldFailAttempt;

			
			//new status
			char isLock = newFailAttempt < 3 ? CommonUtil.FALSE:CommonUtil.TRUE;
			
			//update attempt counter
			int updateStatus = userRepository.updateUserSetFailedloginattemptAndIslockForUsername(newFailAttempt, isLock, username);
	
			
			if (updateStatus == 1)
				logger.info("addFailedLoginAttempt successed!");
			else
				logger.info("addFailedLoginAttempt failed!");

		}
	}
	
	@Override
	@Transactional
	public void resetFailedLoginAttempt(String username) {
		User user = findByUsername(username);
		
		if (user!=null) {
			
			int i = userRepository.updateUserSetFailedloginattemptAndIslockForUsername(0, CommonUtil.FALSE, username);
			if (i == 0)
				logger.info("resetFailedLoginAttempt successed!");
			else
				logger.info("resetFailedLoginAttempt failed!");
		}
	}
    
}
